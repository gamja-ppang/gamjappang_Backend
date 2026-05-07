package demago.gamjappang.domain.post.applicationcore.port.in.command;

import java.util.List;

public record UpdatePostCommand(
        Long postId,
        String title,
        String content,
        List<String> tags,
        Long userId
) {
}
