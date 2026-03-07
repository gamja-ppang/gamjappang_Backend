package demago.gamjappang.user.adapter.in.web.dto;

import demago.gamjappang.user.application.port.in.result.TokenResult;

public record TokenResponse(

        String accessToken,
        String refreshToken
) {
    public static TokenResponse from(TokenResult tokenResult) {
        return new TokenResponse(tokenResult.accessToken(), tokenResult.refreshToken());
    }
}
