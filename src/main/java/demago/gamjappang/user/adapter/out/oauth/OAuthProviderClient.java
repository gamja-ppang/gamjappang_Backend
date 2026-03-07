package demago.gamjappang.user.adapter.out.oauth;

import demago.gamjappang.user.application.port.out.OAuthUserInfo;

public interface OAuthProviderClient {
    String provider();
    OAuthUserInfo fetchUser(String authorizationCode, String redirectUri);
}
