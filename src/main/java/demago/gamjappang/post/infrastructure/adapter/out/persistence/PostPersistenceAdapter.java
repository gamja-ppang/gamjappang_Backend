package demago.gamjappang.post.infrastructure.adapter.out.persistence;

import demago.gamjappang.post.applicationcore.port.out.PostRepositoryPort;
import demago.gamjappang.post.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Post> getPostPage(String tag, String keyword, Pageable pageable) {
        String normalizedTag = normalize(tag);
        String normalizedKeyword = normalize(keyword);

        Page<PostJpaEntity> posts = repository.searchPosts(
                normalizedTag,
                normalizedKeyword,
                pageable
        );

        return posts.map(mapper::toDomain);
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    @Override
    public Optional<Post> finfById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}