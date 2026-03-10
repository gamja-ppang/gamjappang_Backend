package demago.gamjappang.user.infrastructure.adapter.in.web.dto.request;

import demago.gamjappang.user.applicationcore.port.in.command.SendVerificationCodeCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendRequest(

        @NotBlank @Email String email
) {
    public SendVerificationCodeCommand toCommand() {
        return new SendVerificationCodeCommand(email);
    }
}
