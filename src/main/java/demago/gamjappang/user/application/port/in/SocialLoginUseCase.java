package demago.gamjappang.user.application.port.in;

import demago.gamjappang.user.application.port.in.command.SocialLoginCommand;
import demago.gamjappang.user.application.port.in.result.TokenResult;

public interface SocialLoginUseCase {
    TokenResult socialLogin(SocialLoginCommand command);
}
