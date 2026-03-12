package demago.gamjappang.post.infrastructure.adapter.out.persistence;

import demago.gamjappang.post.domain.model.Post;
import demago.gamjappang.user.domain.model.User;
import demago.gamjappang.user.infrastructure.adapter.out.persistence.UserJpaEntity;
import demago.gamjappang.user.infrastructure.adapter.out.persistence.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final UserMapper userMapper;

    public PostJpaEntity toEntity(Post post) {
        return new PostJpaEntity(
                post.getId(),
                userMapper.toEntity(post.getUser()),
                post.getTitle(),
                post.getContent(),
                post.getViewCount(),
                post.getTags(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    public Post toDomain(PostJpaEntity entity) {
        return Post.restore(
                entity.getId(),
                userMapper.toDomain(entity.getUser()),
                entity.getTitle(),
                entity.getContent(),
                entity.getViewCount(),
                entity.getTags(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}