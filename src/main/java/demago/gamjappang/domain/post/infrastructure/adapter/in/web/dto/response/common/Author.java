package demago.gamjappang.domain.post.infrastructure.adapter.in.web.dto.response.common;

import demago.gamjappang.domain.post.applicationcore.port.in.result.CreatePostResult;
import demago.gamjappang.domain.post.applicationcore.port.in.result.GetPostResult;
import demago.gamjappang.domain.post.applicationcore.port.in.result.PostPageResult;
import demago.gamjappang.domain.post.applicationcore.port.in.result.UpdatePostResult;

public record Author(
        Long id,
        String name
) {
    public static Author from(CreatePostResult result) {
        return new Author(result.author().id(), result.author().name());
    }

    public static Author from(UpdatePostResult result) {
        return new Author(result.author().id(), result.author().name());
    }

    public static Author from(PostPageResult.PostSummary result) {
        return new Author(result.author().id(), result.author().name());
    }

    public static Author from(GetPostResult result) {
        return new Author(result.author().id(), result.author().name());
    }
}
