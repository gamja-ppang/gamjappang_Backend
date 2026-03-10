package demago.gamjappang.user.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LogoutRequest(

        @NotBlank String refreshToken
) {}