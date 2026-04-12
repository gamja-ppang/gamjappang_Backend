package demago.gamjappang.post.applicationcore.port.in.result.common;

import demago.gamjappang.post.applicationcore.port.in.result.PostPageResult;
import demago.gamjappang.post.domain.model.Post;

public record Author(
        Long id,
        String name
) {
    public static Author from(Post post) {
        return new Author(
                post.getUser().getId(),
                post.getUser().getUsername()
        );
    }
}