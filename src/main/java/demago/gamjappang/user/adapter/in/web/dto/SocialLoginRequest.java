package demago.gamjappang.user.adapter.in.web.dto;

import demago.gamjappang.user.application.port.in.command.SocialLoginCommand;
import jakarta.validation.constraints.NotBlank;

public record SocialLoginRequest(

        @NotBlank String provider,
        @NotBlank String authorizationCode,
        @NotBlank String redirectUri
) {
    public SocialLoginCommand toCommand() {
        return new SocialLoginCommand(provider, authorizationCode, redirectUri);
    }
}
