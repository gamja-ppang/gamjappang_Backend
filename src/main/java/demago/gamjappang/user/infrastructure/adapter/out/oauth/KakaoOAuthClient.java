package demago.gamjappang.user.infrastructure.adapter.out.oauth;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.user.applicationcore.port.out.OAuthUserInfo;
import demago.gamjappang.user.exception.SocialErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class KakaoOAuthClient implements OAuthProviderClient {

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE =
            new ParameterizedTypeReference<>() {};

    private final RestClient restClient = RestClient.create();

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret:}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri:https://kauth.kakao.com/oauth/token}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri:https://kapi.kakao.com/v2/user/me}")
    private String userinfoUri;

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    @Override
    public String provider() {
        return "kakao";
    }

    @Override
    public OAuthUserInfo fetchUser(String authorizationCode, String redirectUri) {
        try {
            Map<String, Object> token = restClient.post()
                    .uri(tokenUri)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body("grant_type=authorization_code" +
                            "&client_id=" + encode(clientId) +
                            (clientSecret == null || clientSecret.isBlank() ? "" : "&client_secret=" + encode(clientSecret)) +
                            "&redirect_uri=" + encode(redirectUri) +
                            "&code=" + encode(authorizationCode))
                    .retrieve()
                    .body(MAP_TYPE);

            if (token == null) throw new GamjaException(SocialErrorCode.OAUTH_PROVIDER_FAILED);

            String accessToken = (String) token.get("access_token");
            if (accessToken == null) throw new GamjaException(SocialErrorCode.OAUTH_PROVIDER_FAILED);

            Map<String, Object> me = restClient.get()
                    .uri(userinfoUri)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .body(MAP_TYPE);

            Map<String, Object> account = asStringObjectMap(me.get("kakao_account"));
            Map<String, Object> props = asStringObjectMap(me.get("properties"));

            String email = account == null ? null : (String) account.get("email");
            String name = props == null ? "kakao_user" : (String) props.getOrDefault("nickname", "kakao_user");
            if (email == null) throw new GamjaException(SocialErrorCode.OAUTH_PROVIDER_FAILED);

            return new OAuthUserInfo(email, name);
        } catch (GamjaException e) {
            throw e;
        } catch (Exception e) {
            throw new GamjaException(SocialErrorCode.OAUTH_PROVIDER_FAILED);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> asStringObjectMap(Object value) {
        if (value instanceof Map<?, ?> map) {
            return (Map<String, Object>) map;
        }
        return null;
    }
}