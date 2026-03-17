package demago.gamjappang.post.applicationcore.port.out;

import demago.gamjappang.post.applicationcore.port.in.result.CreatePostResult;
import demago.gamjappang.post.applicationcore.port.in.result.UpdatePostResult;
import demago.gamjappang.post.domain.model.Post;

import java.util.Optional;

public interface PostRepositoryPort {
    Post save(Post post);
    Post update(Post post);

    Optional<Post> finfById(Long id);
}