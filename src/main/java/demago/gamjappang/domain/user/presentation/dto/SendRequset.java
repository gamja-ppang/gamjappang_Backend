package demago.gamjappang.domain.user.presentation.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SendRequset {
    @Email
    private String email;
}
