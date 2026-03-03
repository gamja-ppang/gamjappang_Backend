package demago.gamjappang.domain.user.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public class SocialLoginRequest {

    @NotBlank
    private String provider;

    @NotBlank
    private String authorizationCode;

    @NotBlank
    private String redirectUri;

    public String getProvider() { return provider; }
    public String getAuthorizationCode() { return authorizationCode; }
    public String getRedirectUri() { return redirectUri; }

    public void setProvider(String provider) { this.provider = provider; }
    public void setAuthorizationCode(String authorizationCode) { this.authorizationCode = authorizationCode; }
    public void setRedirectUri(String redirectUri) { this.redirectUri = redirectUri; }
}
