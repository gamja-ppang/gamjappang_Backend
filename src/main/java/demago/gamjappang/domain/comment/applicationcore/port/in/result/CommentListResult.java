package demago.gamjappang.domain.comment.applicationcore.port.in.result;

import demago.gamjappang.domain.comment.domain.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record CommentListResult(
        List<CommentSummary> content
) {
    public static CommentListResult from(List<Comment> comments) {
        return new CommentListResult(
                comments.stream()
                        .map(comment -> new CommentSummary(
                                comment.getId(),
                                new Author(
                                        comment.getUser().getId(),
                                        comment.getUser().getUsername()
                                ),
                                comment.getContent(),
                                comment.getCreatedAt()
                        ))
                        .toList()
        );
    }

    public record CommentSummary(
            Long id,
            Author author,
            String content,
            LocalDateTime createdAt
    ){
    }

    public record Author(
            Long userId,
            String username
    ) {
    }
}
