package demago.gamjappang.domain.infrastructure.oauth;

import java.util.List;

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
                .orElse(null);
    }
}
