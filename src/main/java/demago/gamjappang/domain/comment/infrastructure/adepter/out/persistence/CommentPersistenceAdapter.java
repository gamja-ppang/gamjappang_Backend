package demago.gamjappang.domain.comment.infrastructure.adepter.out.persistence;

import demago.gamjappang.domain.comment.applicationcore.port.out.CommentRepositoryPort;
import demago.gamjappang.domain.comment.domain.model.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
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

    @Override
    public Optional<Comment> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        List<CommentJpaEntity> comments = repository.findByPost_IdOrderByCreatedAtDesc(postId);

        return comments.stream().map(mapper::toDomain).toList();
    }
}
