package demago.gamjappang.domain.comment.applicationcore.port.out;

import demago.gamjappang.domain.comment.domain.model.Comment;

import java.util.List;

public interface CommentRepositoryPort {
    void save(Comment comment);

    void delete(Long id);
}
