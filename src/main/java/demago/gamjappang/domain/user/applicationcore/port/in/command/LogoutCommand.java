package demago.gamjappang.domain.user.applicationcore.port.in.command;

public record LogoutCommand(Long userId, String refreshToken) {
}
