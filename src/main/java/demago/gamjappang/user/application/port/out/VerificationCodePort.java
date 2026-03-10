package demago.gamjappang.user.application.port.out;

public interface VerificationCodePort {
    String issue(String email);
    void verify(String email, String code);
}
