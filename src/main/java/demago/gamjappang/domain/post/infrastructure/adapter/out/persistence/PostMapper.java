package demago.gamjappang.domain.post.infrastructure.adapter.out.persistence;

import demago.gamjappang.domain.post.domain.model.Post;
import demago.gamjappang.domain.user.infrastructure.adapter.out.persistence.UserMapper;
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
                post.getTags(),
                post.getViewCount(),
                post.getHeartCount(),
                post.getCommentCount(),
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
                entity.getTags(),
                entity.getViewCount(),
                entity.getHeartCount(),
                entity.getCommentCount(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}