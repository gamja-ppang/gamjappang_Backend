package demago.gamjappang.domain.comment.infrastructure.adepter.out.persistence;

import demago.gamjappang.domain.comment.applicationcore.port.out.CommentRepositoryPort;
import demago.gamjappang.domain.comment.domain.model.Comment;

import java.util.List;

public class CommentPersistenceAdapter implements CommentRepositoryPort {

    private final SpringDataCommentRepository repository;
    private final CommentMapper mapper;

    public CommentPersistenceAdapter(SpringDataCommentRepository repository, CommentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(Comment comment) {
        repository.save(mapper.toEntity(comment));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
