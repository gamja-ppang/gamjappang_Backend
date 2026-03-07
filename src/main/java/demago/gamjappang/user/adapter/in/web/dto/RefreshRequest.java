package demago.gamjappang.user.adapter.in.web.dto;

import demago.gamjappang.user.application.port.in.command.RefreshCommand;
import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(

        @NotBlank String refreshToken
) {
    public RefreshCommand toCommand() {
        return new RefreshCommand(refreshToken);
    }
}

