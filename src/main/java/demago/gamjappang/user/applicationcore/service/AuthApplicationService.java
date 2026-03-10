package demago.gamjappang.user.applicationcore.service;

import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.user.applicationcore.port.in.*;
import demago.gamjappang.user.applicationcore.port.in.command.*;
import demago.gamjappang.user.applicationcore.port.in.result.TokenResult;
import demago.gamjappang.user.applicationcore.port.out.*;
import demago.gamjappang.user.domain.model.User;
import demago.gamjappang.user.exception.UserErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthApplicationService implements
        JoinUseCase,
        SendVerificationCodeUseCase,
        VerifyEmailUseCase,
        LoginUseCase,
        RefreshUseCase,
        LogoutUseCase,
        SocialLoginUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final VerificationCodePort verificationCodePort;
    private final MailSenderPort mailSenderPort;
    private final TokenPort tokenPort;
    private final RefreshTokenPort refreshTokenPort;
    private final OAuthPort oAuthPort;

    public AuthApplicationService(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            VerificationCodePort verificationCodePort,
            MailSenderPort mailSenderPort,
            TokenPort tokenPort,
            RefreshTokenPort refreshTokenPort,
            OAuthPort oAuthPort
    ) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.verificationCodePort = verificationCodePort;
        this.mailSenderPort = mailSenderPort;
        this.tokenPort = tokenPort;
        this.refreshTokenPort = refreshTokenPort;
        this.oAuthPort = oAuthPort;
    }

    @Override
    public void join(JoinCommand command) {
        if (userRepositoryPort.existsByEmail(command.email())) {
            throw new GamjaException(UserErrorCode.EMAIL_ALREADY_EXISTS);
        }
        String encodedPassword = passwordEncoderPort.encode(command.password());
        User user = User.create(command.username(), command.email(), encodedPassword);
        userRepositoryPort.save(user);
    }

    @Override
    public void send(SendVerificationCodeCommand command) {
        String code = verificationCodePort.issue(command.email());
        mailSenderPort.sendVerificationEmail(command.email(), code);
    }

    @Override
    public void verify(VerifyEmailCommand command) {
        User user = userRepositoryPort.findByEmail(command.email())
                .orElseThrow(() -> new GamjaException(UserErrorCode.EMAIL_NOT_FOUND));

        verificationCodePort.verify(command.email(), command.signupCode());
        user.verify();
        userRepositoryPort.save(user);
    }

    @Override
    public TokenResult login(LoginCommand command) {
        User user = userRepositoryPort.findByEmail(command.email())
                .orElseThrow(() -> new GamjaException(UserErrorCode.EMAIL_NOT_FOUND));

        if (!passwordEncoderPort.matches(command.password(), user.getPassword())) {
            throw new GamjaException(UserErrorCode.INVALID_CREDENTIALS);
        }
        if (!user.isVerified()) {
            throw new GamjaException(UserErrorCode.EMAIL_NOT_VERIFIED);
        }
        if (user.isBlocked()) {
            throw new GamjaException(UserErrorCode.USER_BLOCKED);
        }

        String access = tokenPort.createAccessToken(user);
        String refresh = tokenPort.createRefreshToken(user);
        refreshTokenPort.save(user.getId(), refresh);
        return new TokenResult(access, refresh);
    }

    @Override
    public TokenResult refresh(RefreshCommand command) {
        tokenPort.validate(command.refreshToken());
        Long userId = tokenPort.getUserId(command.refreshToken());
        refreshTokenPort.validateMatches(userId, command.refreshToken());

        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new GamjaException(UserErrorCode.USER_NOT_FOUND));

        if (user.isBlocked()) {
            throw new GamjaException(UserErrorCode.USER_BLOCKED);
        }

        String newAccess = tokenPort.createAccessToken(user);
        String newRefresh = tokenPort.createRefreshToken(user);
        refreshTokenPort.save(userId, newRefresh);
        return new TokenResult(newAccess, newRefresh);
    }

    @Override
    public void logout(LogoutCommand command) {
        refreshTokenPort.validateMatches(command.userId(), command.refreshToken());
        refreshTokenPort.delete(command.userId());
    }

    @Override
    public TokenResult socialLogin(SocialLoginCommand command) {
        OAuthUserInfo info = oAuthPort.fetchUser(command.provider(), command.authorizationCode(), command.redirectUri());

        User user = userRepositoryPort.findByEmail(info.email())
                .orElseGet(() -> {
                    String dummyPassword = passwordEncoderPort.encode("LOCAL_LOGIN_ONLY");
                    User newUser = User.create(info.name(), info.email(), dummyPassword);
                    newUser.verify();
                    return userRepositoryPort.save(newUser);
                });

        if (user.isBlocked()) {
            throw new GamjaException(UserErrorCode.USER_BLOCKED);
        }

        String access = tokenPort.createAccessToken(user);
        String refresh = tokenPort.createRefreshToken(user);
        refreshTokenPort.save(user.getId(), refresh);
        return new TokenResult(access, refresh);
    }
}
