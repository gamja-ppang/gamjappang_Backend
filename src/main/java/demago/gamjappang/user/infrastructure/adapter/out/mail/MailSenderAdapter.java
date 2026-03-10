package demago.gamjappang.user.infrastructure.adapter.out.mail;

import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.user.applicationcore.port.out.MailSenderPort;
import demago.gamjappang.user.exception.UserErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderAdapter implements MailSenderPort {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public MailSenderAdapter(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendVerificationEmail(String toEmail, String code) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, toEmail);
            message.setSubject("이메일 인증");
            String body = "<h3>요청하신 인증 번호입니다.</h3><h1>" + code + "</h1><h3>감사합니다.</h3>";
            message.setText(body, "UTF-8", "html");
            javaMailSender.send(message);
        } catch (MessagingException | MailException e) {
            throw new GamjaException(UserErrorCode.EMAIL_SEND_FAILED);
        }
    }
}
