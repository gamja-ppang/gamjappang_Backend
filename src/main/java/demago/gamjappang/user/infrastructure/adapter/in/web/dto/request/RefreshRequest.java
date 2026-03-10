package demago.gamjappang.user.infrastructure.adapter.in.web.dto.request;

import demago.gamjappang.user.applicationcore.port.in.command.RefreshCommand;
import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(

        @NotBlank String refreshToken
) {
    public RefreshCommand toCommand() {
        return new RefreshCommand(refreshToken);
    }
}

