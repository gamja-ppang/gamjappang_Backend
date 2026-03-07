package demago.gamjappang.user.application.port.in;

import demago.gamjappang.user.application.port.in.command.RefreshCommand;
import demago.gamjappang.user.application.port.in.result.TokenResult;

public interface RefreshUseCase {
    TokenResult refresh(RefreshCommand command);
}
