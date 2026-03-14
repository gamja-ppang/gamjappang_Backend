package demago.gamjappang.post.applicationcore.port.out;

import demago.gamjappang.post.applicationcore.port.in.result.CreatePostResult;
import demago.gamjappang.post.domain.model.Post;

public interface PostRepositoryPort {
    CreatePostResult save(Post post);
}