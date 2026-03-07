package demago.gamjappang.global.security.userdetails;

import demago.gamjappang.user.application.port.out.UserRepositoryPort;
import demago.gamjappang.user.exception.UserErrorCode;
import demago.gamjappang.user.adapter.out.persistence.SpringDataUserJpaRepository;
import demago.gamjappang.global.error.exception.GamjaException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalService implements UserDetailsService {

    private final UserRepositoryPort userRepositoryPort;

    public UserPrincipalService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepositoryPort.findByEmail(email)
                .map(UserPrincipal::from)
                .orElseThrow(() -> new GamjaException(UserErrorCode.EMAIL_NOT_FOUND));
    }

    public UserPrincipal loadById(Long id) {
        return userRepositoryPort.findById(id)
                .map(UserPrincipal::from)
                .orElseThrow(() -> new GamjaException(UserErrorCode.USER_NOT_FOUND));
    }
}
