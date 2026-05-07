package demago.gamjappang.domain.post.infrastructure.adapter.in.web.dto.response;

import demago.gamjappang.domain.post.applicationcore.port.in.result.UpdatePostResult;
import demago.gamjappang.domain.post.infrastructure.adapter.in.web.dto.response.common.Author;

import java.time.LocalDateTime;
import java.util.List;

public record UpdatePostResponse(
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
    public static UpdatePostResponse from(UpdatePostResult result) {
        return new UpdatePostResponse(
                result.id(),
                Author.from(result),
                result.title(),
                result.content(),
                result.tags(),
                result.viewCount(),
                result.heartCount(),
                result.commentCount(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
