package demago.gamjappang.user.application.port.out;

public interface OAuthPort {
    OAuthUserInfo fetchUser(String provider, String authorizationCode, String redirectUri);
}
