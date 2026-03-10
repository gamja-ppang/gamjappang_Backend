package demago.gamjappang.user.applicationcore.port.out;

public interface MailSenderPort {
    void sendVerificationEmail(String email, String code);
}
