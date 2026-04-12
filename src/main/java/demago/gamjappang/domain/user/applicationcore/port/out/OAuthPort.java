package demago.gamjappang.domain.user.applicationcore.port.out;

public interface OAuthPort {
    OAuthUserInfo fetchUser(String provider, String authorizationCode, String redirectUri);
}
