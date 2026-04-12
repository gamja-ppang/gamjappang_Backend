package demago.gamjappang.domain.post.applicationcore.port.out;

import demago.gamjappang.domain.post.domain.model.Post;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PostRepositoryPort {
    Post save(Post post);
    Post update(Post post);
    void delete(Long id);

    Page<Post> getPostPage(String tag, String keyword, int page, int size, String sortBy, String direction);

    Optional<Post> findById(Long id);
}