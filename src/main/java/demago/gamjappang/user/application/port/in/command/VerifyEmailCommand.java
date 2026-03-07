package demago.gamjappang.user.application.port.in.command;

public record VerifyEmailCommand(String email, String signupCode) {
}
