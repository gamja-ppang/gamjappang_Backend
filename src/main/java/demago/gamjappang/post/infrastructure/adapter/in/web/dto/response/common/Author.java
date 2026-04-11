package demago.gamjappang.post.infrastructure.adapter.in.web.dto.response.common;

import demago.gamjappang.post.applicationcore.port.in.result.CreatePostResult;
import demago.gamjappang.post.applicationcore.port.in.result.PostPageResult;
import demago.gamjappang.post.applicationcore.port.in.result.UpdatePostResult;

public record Author(
        Long id,
        String name
) {
    public static Author from(CreatePostResult.Author author) {
        return new Author(author.id(), author.name());
    }

    public static Author from(UpdatePostResult.Author author) {
        return new Author(author.id(), author.name());
    }

    public static Author from(PostPageResult.PostSummary.Author author) {
        return new Author(author.id(), author.name());
    }
}
