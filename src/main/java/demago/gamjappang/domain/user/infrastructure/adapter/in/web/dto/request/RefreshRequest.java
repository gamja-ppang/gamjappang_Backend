package demago.gamjappang.domain.user.infrastructure.adapter.in.web.dto.request;

import demago.gamjappang.domain.user.applicationcore.port.in.command.RefreshCommand;
import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(

        @NotBlank String refreshToken
) {
    public RefreshCommand toCommand() {
        return new RefreshCommand(refreshToken);
    }
}

