package demago.gamjappang.post.infrastructure.adapter.in.web.dto.request;

import demago.gamjappang.post.applicationcore.port.in.command.CreatePostCommand;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreatePostRequest(

        @NotBlank String title,
        @NotBlank String content,
        List<String> tags
) {
    public CreatePostCommand toCreatePostCommand(Long userId) {
        return new CreatePostCommand(title, content, tags, userId);
    }
}
