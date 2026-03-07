package demago.gamjappang.user.application.port.in.command;

public record LogoutCommand(Long userId, String refreshToken) {
}
