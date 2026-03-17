package demago.gamjappang.post.applicationcore.port.in.result;

import java.time.LocalDateTime;
import java.util.List;

public record UpdatePostResult(
        Long id,
        Author author,
        String title,
        String content,
        List<String> tags,
        int viewCount,
        int heartCount,
        int commentCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public record Author(
            Long id,
            String name
    ) {
    }
}
