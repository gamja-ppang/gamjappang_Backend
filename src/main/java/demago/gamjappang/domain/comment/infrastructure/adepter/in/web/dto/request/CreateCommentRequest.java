package demago.gamjappang.domain.comment.infrastructure.adepter.in.web.dto.request;

import demago.gamjappang.domain.comment.applicationcore.port.in.command.CreateCommentCommand;

public record CreateCommentRequest(
        Long postId,
        String content
) {
    public CreateCommentCommand toCreateCommentCommand(Long userId) {
        return new CreateCommentCommand(postId, userId, content);
    }
}
