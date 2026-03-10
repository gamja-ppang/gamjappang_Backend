package demago.gamjappang.user.applicationcore.port.in;

import demago.gamjappang.user.applicationcore.port.in.command.LoginCommand;
import demago.gamjappang.user.applicationcore.port.in.result.TokenResult;

public interface LoginUseCase {
    TokenResult login(LoginCommand command);
}
