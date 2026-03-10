package demago.gamjappang.user.adapter.in.web.dto;

import demago.gamjappang.user.application.port.in.command.LoginCommand;
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
