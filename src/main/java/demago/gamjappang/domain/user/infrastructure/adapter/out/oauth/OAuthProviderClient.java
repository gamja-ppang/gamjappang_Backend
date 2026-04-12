package demago.gamjappang.domain.user.infrastructure.adapter.out.oauth;

import demago.gamjappang.domain.user.applicationcore.port.out.OAuthUserInfo;

public interface OAuthProviderClient {
    String provider();
    OAuthUserInfo fetchUser(String authorizationCode, String redirectUri);
}
