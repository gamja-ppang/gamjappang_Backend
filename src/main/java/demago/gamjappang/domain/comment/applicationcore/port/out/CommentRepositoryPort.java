package demago.gamjappang.domain.comment.applicationcore.port.out;

import demago.gamjappang.domain.comment.domain.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryPort {
    void save(Comment comment);

    void delete(Long id);

    Optional<Comment> findById(Long id);

    List<Comment> findByPostId(Long postId);
}
