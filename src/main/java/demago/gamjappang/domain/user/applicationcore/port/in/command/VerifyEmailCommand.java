package demago.gamjappang.domain.user.applicationcore.port.in.command;

public record VerifyEmailCommand(String email, String signupCode) {
}
