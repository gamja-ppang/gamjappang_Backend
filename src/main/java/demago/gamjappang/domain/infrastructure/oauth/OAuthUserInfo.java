package demago.gamjappang.domain.infrastructure.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OAuthUserInfo {
    private final String email;
    private final String name;
}
