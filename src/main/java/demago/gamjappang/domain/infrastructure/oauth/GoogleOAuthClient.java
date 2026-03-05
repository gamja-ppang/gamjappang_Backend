package demago.gamjappang.domain.infrastructure.oauth;

import java.util.Map;

import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.global.security.userdetails.exception.SocialErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GoogleOAuthClient implements OAuthProviderClient {

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE =
            new ParameterizedTypeReference<>() {};

    private final RestClient restClient = RestClient.create();

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    // google provider.* 는 네 properties에 없어서(지금) 기본값 fallback 유지
    @Value("${spring.security.oauth2.client.provider.google.token-uri:https://oauth2.googleapis.com/token}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri:https://openidconnect.googleapis.com/v1/userinfo}")
    private String userinfoUri;

    @Override
    public String provider() {
        return "google";
    }

    @Override
    public OAuthUserInfo fetchUser(String authorizationCode, String redirectUri) {
        try {
            Map<String, Object> token = restClient.post()
                    .uri(tokenUri)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body("code=" + authorizationCode +
                            "&client_id=" + clientId +
                            "&client_secret=" + clientSecret +
                            "&redirect_uri=" + redirectUri +
                            "&grant_type=authorization_code")
                    .retrieve()
                    .body(MAP_TYPE);

            String accessToken = (String) token.get("access_token");
            if (accessToken == null) throw new GamjaException(SocialErrorCode.OAUTH_PROVIDER_FAILED);

            Map<String, Object> userinfo = restClient.get()
                    .uri(userinfoUri)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .body(MAP_TYPE);

            String email = (String) userinfo.get("email");
            String name = (String) userinfo.getOrDefault("name", "google_user");
            if (email == null) throw new GamjaException(SocialErrorCode.OAUTH_PROVIDER_FAILED);

            return new OAuthUserInfo(email, name);
        } catch (Exception e) {
            throw new GamjaException(SocialErrorCode.OAUTH_PROVIDER_FAILED);
        }
    }
}