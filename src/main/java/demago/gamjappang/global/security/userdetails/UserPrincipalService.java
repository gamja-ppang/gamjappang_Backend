package demago.gamjappang.global.security.userdetails;

import demago.gamjappang.domain.user.exception.UserErrorCode;
import demago.gamjappang.domain.user.repository.UserRepository;
import demago.gamjappang.global.error.exception.GamjaException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserPrincipalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // (로컬 로그인 시) email로 로드
    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .map(UserPrincipal::from)
                .orElseThrow(() -> new GamjaException(UserErrorCode.EMAIL_NOT_FOUND));
    }

    // (JWT 인증 시) id로 로드
    public UserPrincipal loadById(Long id) {
        return userRepository.findById(id)
                .map(UserPrincipal::from)
                .orElseThrow(() -> new GamjaException(UserErrorCode.USER_NOT_FOUND));
    }
}
