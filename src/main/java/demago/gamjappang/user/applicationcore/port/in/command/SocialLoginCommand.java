package demago.gamjappang.user.applicationcore.port.in.command;

public record SocialLoginCommand(String provider, String authorizationCode, String redirectUri) {
}
