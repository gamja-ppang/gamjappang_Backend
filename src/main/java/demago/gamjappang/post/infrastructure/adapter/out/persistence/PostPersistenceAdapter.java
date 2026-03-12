package demago.gamjappang.post.infrastructure.adapter.out.persistence;

import demago.gamjappang.post.applicationcore.port.out.PostRepositoryPort;
import demago.gamjappang.post.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public class PostPersistenceAdapter implements PostRepositoryPort {

    private final SpringDataPostJpaRepository repository;
    private final PostMapper mapper;

    public  PostPersistenceAdapter(SpringDataPostJpaRepository repository, PostMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Post save(Post post) {
        return mapper.toDomain(repository.save(mapper.toEntity(post)));
    }
}
