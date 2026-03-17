package demago.gamjappang.post.infrastructure.adapter.out.persistence;

import demago.gamjappang.post.applicationcore.port.in.result.CreatePostResult;
import demago.gamjappang.post.applicationcore.port.in.result.UpdatePostResult;
import demago.gamjappang.post.applicationcore.port.out.PostRepositoryPort;
import demago.gamjappang.post.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostPersistenceAdapter implements PostRepositoryPort {

    private final SpringDataPostJpaRepository repository;
    private final PostMapper mapper;

    public  PostPersistenceAdapter(SpringDataPostJpaRepository repository, PostMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Post save(Post post) {
        repository.save(mapper.toEntity(post));

        return post;
    }

    @Override
    public Post update(Post post) {
        repository.save(mapper.toEntity(post));

        return post;
    }

    @Override
    public Optional<Post> finfById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}