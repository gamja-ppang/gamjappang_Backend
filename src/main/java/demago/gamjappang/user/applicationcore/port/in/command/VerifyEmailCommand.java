package demago.gamjappang.user.applicationcore.port.in.command;

public record VerifyEmailCommand(String email, String signupCode) {
}
