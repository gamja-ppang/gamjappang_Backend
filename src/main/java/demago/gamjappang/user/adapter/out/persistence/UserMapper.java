package demago.gamjappang.user.adapter.out.persistence;
import demago.gamjappang.user.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserJpaEntity toEntity(User user) {
        return new UserJpaEntity(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.isVerified(),
                user.isBlocked()
        );
    }

    public User toDomain(UserJpaEntity entity) {
        return User.restore(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole(),
                entity.isVerified(),
                entity.isBlocked()
        );
    }
}

