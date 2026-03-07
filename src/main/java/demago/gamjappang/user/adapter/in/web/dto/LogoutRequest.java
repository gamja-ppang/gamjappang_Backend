package demago.gamjappang.user.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record LogoutRequest(

        @NotBlank String refreshToken
) {}