package demago.gamjappang.post.infrastructure.adapter.in.web.dto.response;

import demago.gamjappang.post.applicationcore.port.in.result.CreatePostResult;

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
                Author.from(result.author()),
                result.title(),
                result.content(),
                result.tags(),
                result.createdAt(),
                result.updatedAt()
        );
    }

    public record Author(
        Long id,
        String name
    ) {
        public static Author from(CreatePostResult.Author author) {
            return new Author(
                    author.id(),
                    author.name()
            );
        }
    }
}
