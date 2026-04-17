package demago.gamjappang.domain.comment.applicationcore.port.in.command;

public record CreateCommentCommand(
        Long postId,
        Long userId,
        String content
) {
}
