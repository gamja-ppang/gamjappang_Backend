package demago.gamjappang.user.adapter.in.web.dto;

import demago.gamjappang.user.application.port.in.command.VerifyEmailCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VerifyRequest(

        @NotBlank @Email String email,
        @NotBlank @Size(min = 4, max = 10) String signupCode
) {
    public VerifyEmailCommand toCommand() {
        return new VerifyEmailCommand(email, signupCode);
    }
}
