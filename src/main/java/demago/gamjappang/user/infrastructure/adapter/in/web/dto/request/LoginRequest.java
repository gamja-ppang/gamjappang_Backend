package demago.gamjappang.user.infrastructure.adapter.in.web.dto.request;

import demago.gamjappang.user.applicationcore.port.in.command.LoginCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank @Email String email,
        @NotBlank String password
) {
    public LoginCommand toCommand() {
        return new LoginCommand(email, password);
    }
}
