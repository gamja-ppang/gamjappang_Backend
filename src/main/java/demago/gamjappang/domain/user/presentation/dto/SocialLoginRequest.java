package demago.gamjappang.domain.user.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record SocialLoginRequest (
        @NotBlank
        String provider,

        @NotBlank
        String authorizationCode,

        @NotBlank
        String redirectUri
) {}