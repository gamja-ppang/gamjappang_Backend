package demago.gamjappang.post.applicationcore.port.out;

import demago.gamjappang.post.domain.model.Post;

public interface PostRepositoryPort {
    Post save(Post post);
}
