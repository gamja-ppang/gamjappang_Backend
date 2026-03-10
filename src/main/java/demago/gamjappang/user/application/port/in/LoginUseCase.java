package demago.gamjappang.user.application.port.in;

import demago.gamjappang.user.application.port.in.command.LoginCommand;
import demago.gamjappang.user.application.port.in.result.TokenResult;

public interface LoginUseCase {
    TokenResult login(LoginCommand command);
}
