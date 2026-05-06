package demago.gamjappang.domain.comment.applicationcore.port.in.command;

public record DeleteCommentCommand(
        Long userId,
        Long commentId
) {
}
