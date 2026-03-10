package demago.gamjappang.user.infrastructure.adapter.out.persistence;

import demago.gamjappang.user.applicationcore.port.out.UserRepositoryPort;
import demago.gamjappang.user.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final SpringDataUserJpaRepository repository;
    private final UserMapper mapper;

    public UserPersistenceAdapter(SpringDataUserJpaRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(repository.save(mapper.toEntity(user)));
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}

