package demago.gamjappang.user.adapter.in.web.dto;

import demago.gamjappang.user.application.port.in.command.JoinCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JoinRequest(

        @NotBlank @Size(max = 50) String username,
        @NotBlank @Email @Size(max = 120) String email,
        @NotBlank @Size(min = 8, max = 72) String password
) {
    public JoinCommand toCommand() {
        return new JoinCommand(username, email, password);
    }
}