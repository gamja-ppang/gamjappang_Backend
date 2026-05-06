package demago.gamjappang.domain.comment.infrastructure.adepter.in.web.dto.request;

import demago.gamjappang.domain.comment.applicationcore.port.in.command.CreateCommentCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCommentRequest(
        @NotNull
        Long postId,

        @NotBlank
        @Size(max = 250)
        String content
) {
    public CreateCommentCommand toCreateCommentCommand(Long userId) {
        return new CreateCommentCommand(postId, userId, content);
    }
}
