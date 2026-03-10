package demago.gamjappang.user.infrastructure.adapter.in.web.dto.request;

import demago.gamjappang.user.applicationcore.port.in.command.SocialLoginCommand;
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
