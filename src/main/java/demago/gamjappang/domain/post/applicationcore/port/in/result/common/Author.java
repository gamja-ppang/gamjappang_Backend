package demago.gamjappang.domain.post.applicationcore.port.in.result.common;

import demago.gamjappang.domain.post.domain.model.Post;

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