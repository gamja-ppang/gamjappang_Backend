package demago.gamjappang.domain.user.applicationcore.port.in;

import demago.gamjappang.domain.user.applicationcore.port.in.command.JoinCommand;

public interface JoinUseCase {
    void join(JoinCommand command);
}
