package demago.gamjappang.domain.infrastructure.oauth;

public interface OAuthProviderClient {
    String provider();
    OAuthUserInfo fetchUser(String authorizationCode, String redirectUri);
}
