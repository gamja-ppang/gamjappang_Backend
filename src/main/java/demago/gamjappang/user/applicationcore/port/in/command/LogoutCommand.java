package demago.gamjappang.user.applicationcore.port.in.command;

public record LogoutCommand(Long userId, String refreshToken) {
}
