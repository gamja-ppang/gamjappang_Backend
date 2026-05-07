package demago.gamjappang.domain.comment.infrastructure.adepter.out.persistence;

import demago.gamjappang.domain.comment.domain.model.Comment;
import demago.gamjappang.domain.post.domain.model.Post;
import demago.gamjappang.domain.post.infrastructure.adapter.out.persistence.PostJpaEntity;
import demago.gamjappang.domain.post.infrastructure.adapter.out.persistence.PostMapper;
import demago.gamjappang.domain.user.domain.model.User;
import demago.gamjappang.domain.user.infrastructure.adapter.out.persistence.UserJpaEntity;
import demago.gamjappang.domain.user.infrastructure.adapter.out.persistence.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    private final PostMapper postMapper;
    private final UserMapper userMapper;

    public CommentMapper(PostMapper postMapper, UserMapper userMapper) {
        this.postMapper = postMapper;
        this.userMapper = userMapper;
    }

    public Comment toDomain(CommentJpaEntity entity) {
        Post post = postMapper.toDomain(entity.getPost());
        User user = userMapper.toDomain(entity.getUser());

        return new Comment(
                entity.getId(),
                post,
                user,
                entity.getContent(),
                entity.getCreatedAt()
        );
    }

    public CommentJpaEntity toEntity(Comment comment) {
        PostJpaEntity postEntity = postMapper.toEntity(comment.getPost());
        UserJpaEntity userEntity = userMapper.toEntity(comment.getUser());

        return new CommentJpaEntity(
                comment.getId(),
                postEntity,
                userEntity,
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
