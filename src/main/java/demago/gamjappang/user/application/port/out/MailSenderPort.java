package demago.gamjappang.user.application.port.out;

public interface MailSenderPort {
    void sendVerificationEmail(String email, String code);
}
