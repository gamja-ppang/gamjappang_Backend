package demago.gamjappang.domain.user.applicationcore.port.out;

import demago.gamjappang.domain.user.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
