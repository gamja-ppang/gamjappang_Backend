package demago.gamjappang.user.adapter.in.web.dto;

import demago.gamjappang.user.application.port.in.command.SendVerificationCodeCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendRequest(

        @NotBlank @Email String email
) {
    public SendVerificationCodeCommand toCommand() {
        return new SendVerificationCodeCommand(email);
    }
}
