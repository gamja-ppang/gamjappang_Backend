package demago.gamjappang.post.applicationcore.port.in.command;

public record DeletePostCommand(
        Long postId,
        Long userId
) {
}
