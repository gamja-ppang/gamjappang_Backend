package demago.gamjappang.domain.user.applicationcore.port.in.command;

public record JoinCommand(String username, String email, String password) {
}
