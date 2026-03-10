package demago.gamjappang.user.infrastructure.adapter.in.web.dto.response;

import demago.gamjappang.user.applicationcore.port.in.result.TokenResult;

public record TokenResponse(

        String accessToken,
        String refreshToken
) {
    public static TokenResponse from(TokenResult tokenResult) {
        return new TokenResponse(tokenResult.accessToken(), tokenResult.refreshToken());
    }
}
