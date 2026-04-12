package demago.gamjappang.post.infrastructure.adapter.in.web.dto.request;

import demago.gamjappang.post.applicationcore.port.in.command.UpdatePostCommand;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdatePostRequest(

        @NotBlank String title,
        @NotBlank String content,
        List<String> tags
) {
    public UpdatePostCommand toUpdateCommand(Long postId, Long userId) {
        return new UpdatePostCommand(postId, title, content, tags, userId);
    }
}