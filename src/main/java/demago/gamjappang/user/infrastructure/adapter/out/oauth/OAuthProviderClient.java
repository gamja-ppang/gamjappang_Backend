package demago.gamjappang.user.infrastructure.adapter.out.oauth;

import demago.gamjappang.user.applicationcore.port.out.OAuthUserInfo;

public interface OAuthProviderClient {
    String provider();
    OAuthUserInfo fetchUser(String authorizationCode, String redirectUri);
}
