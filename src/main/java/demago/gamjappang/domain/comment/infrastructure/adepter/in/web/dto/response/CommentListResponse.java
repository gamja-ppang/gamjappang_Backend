package demago.gamjappang.domain.comment.infrastructure.adepter.in.web.dto.response;

import demago.gamjappang.domain.comment.applicationcore.port.in.result.CommentListResult;

import java.time.LocalDateTime;
import java.util.List;

public record CommentListResponse(
        List<CommentResponse> content
) {
    public static CommentListResponse from(CommentListResult result) {
        return new CommentListResponse(
                result.content().stream()
                        .map(comment -> new CommentResponse(
                                comment.id(),
                                new AuthorResponse(
                                        comment.author().userId(),
                                        comment.author().username()
                                ),
                                comment.content(),
                                comment.createdAt()
                        ))
                        .toList()
        );
    }

    public record CommentResponse(
            Long id,
            AuthorResponse author,
            String content,
            LocalDateTime createdAt
    ) {
    }

    public record AuthorResponse(
            Long userId,
            String username
    ) {
    }
}
