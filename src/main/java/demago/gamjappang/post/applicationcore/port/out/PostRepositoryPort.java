package demago.gamjappang.post.applicationcore.port.out;

import demago.gamjappang.post.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostRepositoryPort {
    Post save(Post post);
    Post update(Post post);
    void delete(Long id);

    Page<Post> getPostPage(String tag, String keyword, int page, int size, String sortBy, String direction);

    Optional<Post> findById(Long id);
}