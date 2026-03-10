package demago.gamjappang.user.applicationcore.port.in;

import demago.gamjappang.user.applicationcore.port.in.command.RefreshCommand;
import demago.gamjappang.user.applicationcore.port.in.result.TokenResult;

public interface RefreshUseCase {
    TokenResult refresh(RefreshCommand command);
}
