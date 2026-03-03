package demago.gamjappang.domain.user.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class VerifyRequest {

    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 4, max = 10)
    private String signupCode;

    public String getEmail() { return email; }
    public String getSignupCode() { return signupCode; }

    public void setEmail(String email) { this.email = email; }
    public void setSignupCode(String signupCode) { this.signupCode = signupCode; }
}
