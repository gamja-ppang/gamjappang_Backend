package demago.gamjappang.user.applicationcore.port.out;

public interface VerificationCodePort {
    String issue(String email);
    void verify(String email, String code);
}
