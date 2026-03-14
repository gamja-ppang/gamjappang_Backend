package demago.gamjappang.post.infrastructure.adapter.out.persistence;

import demago.gamjappang.post.applicationcore.port.in.result.CreatePostResult;
import demago.gamjappang.post.applicationcore.port.out.PostRepositoryPort;
import demago.gamjappang.post.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class PostPersistenceAdapter implements PostRepositoryPort {

    private final SpringDataPostJpaRepository repository;
    private final PostMapper mapper;

    public  PostPersistenceAdapter(SpringDataPostJpaRepository repository, PostMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CreatePostResult save(Post post) {
        repository.save(mapper.toEntity(post));

        return new CreatePostResult(
                post.getId(),
                new CreatePostResult.Author(post.getUser().getId(), post.getUser().getUsername()),
                post.getTitle(),
                post.getContent(),
                post.getTags(),
                post.getCreatedAt(),
                post.getUpdatedAt()
                );
    }
}