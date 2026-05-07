package demago.gamjappang.domain.user.applicationcore.port.in.command;

public record SocialLoginCommand(String provider, String authorizationCode, String redirectUri) {
}
