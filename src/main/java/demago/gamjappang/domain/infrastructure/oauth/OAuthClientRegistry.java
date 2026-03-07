package demago.gamjappang.domain.infrastructure.oauth;

import java.util.List;

import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.global.security.userdetails.exception.SocialErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OAuthClientRegistry {

    private final List<OAuthProviderClient> clients;

    public OAuthProviderClient get(String provider) {
        return clients.stream()
                .filter(c -> c.provider().equalsIgnoreCase(provider))
                .findFirst()
                .orElseThrow(() -> new GamjaException(SocialErrorCode.OAUTH_PROVIDER_FAILED));
    }
}
