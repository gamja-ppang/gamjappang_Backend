package demago.gamjappang.domain.post.infrastructure.adapter.in.web.dto.response;

import demago.gamjappang.domain.post.applicationcore.port.in.result.CreatePostResult;
import demago.gamjappang.domain.post.infrastructure.adapter.in.web.dto.response.common.Author;

import java.time.LocalDateTime;
import java.util.List;

public record CreatePostResponse(

        Long id,
        Author author,
        String title,
        String content,
        List<String> tags,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CreatePostResponse from(CreatePostResult result) {
        return new CreatePostResponse(
                result.id(),
                Author.from(result),
                result.title(),
                result.content(),
                result.tags(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
