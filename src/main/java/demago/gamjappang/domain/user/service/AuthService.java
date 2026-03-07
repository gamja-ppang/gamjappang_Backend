package demago.gamjappang.domain.user.service;

import demago.gamjappang.domain.infrastructure.mail.MailService;
import demago.gamjappang.domain.infrastructure.mail.SignupCodeService;
import demago.gamjappang.domain.user.entity.User;
import demago.gamjappang.domain.user.exception.UserErrorCode;
import demago.gamjappang.domain.user.presentation.dto.TokenResponse;
import demago.gamjappang.domain.user.repository.UserRepository;
import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.global.security.jwt.JwtTokenProvider;
import demago.gamjappang.domain.infrastructure.redis.RefreshTokenStore;
import demago.gamjappang.global.security.userdetails.UserPrincipal;
import demago.gamjappang.global.security.userdetails.exception.SocialErrorCode;
import demago.gamjappang.domain.infrastructure.oauth.OAuthClientRegistry;
import demago.gamjappang.domain.infrastructure.oauth.OAuthUserInfo;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SignupCodeService signupCodeService;
    private final MailService mailService;
    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenStore refreshTokenStore;
    private final OAuthClientRegistry oauthRegistry;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            SignupCodeService signupCodeService,
            MailService mailService,
            JwtTokenProvider tokenProvider,
            RefreshTokenStore refreshTokenStore,
            OAuthClientRegistry oauthRegistry
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.signupCodeService = signupCodeService;
        this.mailService = mailService;
        this.tokenProvider = tokenProvider;
        this.refreshTokenStore = refreshTokenStore;
        this.oauthRegistry = oauthRegistry;
    }

    @Transactional
    public void join(String username, String email, String rawPassword) {
        if (userRepository.existsByEmail(email)) {
            throw new GamjaException(UserErrorCode.EMAIL_ALREADY_EXISTS);
        }
        String encodedPassword = passwordEncoder.encode(rawPassword);

        User user = User.of(username, email, encodedPassword);
        userRepository.save(user);
    }

    @Transactional
    public void sendVerificationCode(String email) {
        String code = signupCodeService.issue(email);
        mailService.sendVerificationEmail(email, code);
    }

    @Transactional
    public void verifyEmail(String email, String signupCode) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GamjaException(UserErrorCode.EMAIL_NOT_FOUND));

        signupCodeService.verify(email, signupCode);
        user.verify();
    }

    public TokenResponse login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GamjaException(UserErrorCode.EMAIL_NOT_FOUND));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new GamjaException(UserErrorCode.INVALID_CREDENTIALS);
        }
        if (!user.isVerified()) {
            throw new GamjaException(UserErrorCode.EMAIL_NOT_VERIFIED);
        }
        if (!user.isBlocked()) {
            throw new GamjaException(UserErrorCode.USER_BLOCKED);
        }

        String access = tokenProvider.createAccessToken(user);
        String refresh = tokenProvider.createRefreshToken(user);
        refreshTokenStore.save(user.getId(), refresh);

        return new TokenResponse(access, refresh);
    }

    public TokenResponse refresh(String refreshToken) {
        tokenProvider.validate(refreshToken);

        Long userId = tokenProvider.getUserId(refreshToken);
        refreshTokenStore.validateMatches(userId, refreshToken);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamjaException(UserErrorCode.USER_NOT_FOUND));

        String newAccess = tokenProvider.createAccessToken(user);
        String newRefresh = tokenProvider.createRefreshToken(user);
        refreshTokenStore.save(userId, newRefresh);

        return new TokenResponse(newAccess, newRefresh);
    }

    public void logout(UserPrincipal principal, String refreshToken) {
        // accessToken은 이미 필터에서 검증되어 principal이 들어온 상태
        refreshTokenStore.validateMatches(principal.getId(), refreshToken);
        refreshTokenStore.delete(principal.getId());
    }

    @Transactional
    public TokenResponse socialLogin(String provider, String authorizationCode, String redirectUri) {
        var client = oauthRegistry.get(provider);

        OAuthUserInfo info = client.fetchUser(authorizationCode, redirectUri);

        User user = userRepository.findByEmail(info.getEmail()).orElseGet(() -> {
            String dummyPw = passwordEncoder.encode("LOCAL_LOGIN_ONLY");

            User newSocialUser = User.of(
                    info.getName(),
                    info.getEmail(),
                    dummyPw
            );

            newSocialUser.verify();

            return userRepository.save(newSocialUser);

//            return userRepository.save(new User(
//                    info.getName(),
//                    info.getEmail(),
//                    dummyPw,
//                    Role.USER,
//                    true,
//                    false
//            ));
        });

        String access = tokenProvider.createAccessToken(user);
        String refresh = tokenProvider.createRefreshToken(user);
        refreshTokenStore.save(user.getId(), refresh);

        return new TokenResponse(access, refresh);
    }
}
