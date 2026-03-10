package demago.gamjappang.user.infrastructure.adapter.out.oauth;

import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.user.applicationcore.port.out.OAuthPort;
import demago.gamjappang.user.applicationcore.port.out.OAuthUserInfo;
import demago.gamjappang.user.exception.SocialErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OAuthClientRegistry implements OAuthPort {

    private final List<OAuthProviderClient> clients;

    public OAuthClientRegistry(List<OAuthProviderClient> clients) {
        this.clients = clients;
    }

    @Override
    public OAuthUserInfo fetchUser(String provider, String authorizationCode, String redirectUri) {
        return clients.stream()
                .filter(client -> client.provider().equalsIgnoreCase(provider))
                .findFirst()
                .orElseThrow(() -> new GamjaException(SocialErrorCode.OAUTH_PROVIDER_FAILED))
                .fetchUser(authorizationCode, redirectUri);
    }
}
