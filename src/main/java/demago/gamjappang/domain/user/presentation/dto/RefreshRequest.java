package demago.gamjappang.domain.user.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest (

        @NotBlank
        String refreshToken
) {}
