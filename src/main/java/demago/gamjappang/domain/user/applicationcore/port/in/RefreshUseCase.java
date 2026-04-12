package demago.gamjappang.domain.user.applicationcore.port.in;

import demago.gamjappang.domain.user.applicationcore.port.in.command.RefreshCommand;
import demago.gamjappang.domain.user.applicationcore.port.in.result.TokenResult;

public interface RefreshUseCase {
    TokenResult refresh(RefreshCommand command);
}
