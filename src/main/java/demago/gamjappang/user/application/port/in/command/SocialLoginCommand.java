package demago.gamjappang.user.application.port.in.command;

public record SocialLoginCommand(String provider, String authorizationCode, String redirectUri) {
}
