package demago.gamjappang.post.applicationcore.port.in.result;

import demago.gamjappang.post.applicationcore.port.in.result.common.Author;

import java.time.LocalDateTime;
import java.util.List;

public record CreatePostResult(
        Long id,
        Author author,
        String title,
        String content,
        List<String> tags,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
