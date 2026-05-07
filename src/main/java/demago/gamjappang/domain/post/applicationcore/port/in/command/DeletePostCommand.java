package demago.gamjappang.domain.post.applicationcore.port.in.command;

public record DeletePostCommand(
        Long postId,
        Long userId
) {
}
