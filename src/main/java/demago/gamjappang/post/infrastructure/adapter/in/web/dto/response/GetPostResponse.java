package demago.gamjappang.post.infrastructure.adapter.in.web.dto.response;

import demago.gamjappang.post.applicationcore.port.in.result.GetPostResult;
import demago.gamjappang.post.infrastructure.adapter.in.web.dto.response.common.Author;

import java.time.LocalDateTime;
import java.util.List;

public record GetPostResponse(
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
    public static GetPostResponse from(GetPostResult result) {
        return new GetPostResponse(
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