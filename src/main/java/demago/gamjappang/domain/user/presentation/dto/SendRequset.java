package demago.gamjappang.domain.user.presentation.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public record SendRequset (

        @Email
        String email
) {}
