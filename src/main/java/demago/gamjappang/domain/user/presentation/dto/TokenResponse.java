package demago.gamjappang.domain.user.presentation.dto;

public record TokenResponse(

        String accessToken,
        String refreshToken
) {}