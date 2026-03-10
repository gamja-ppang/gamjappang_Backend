package demago.gamjappang.user.applicationcore.port.in;

import demago.gamjappang.user.applicationcore.port.in.command.JoinCommand;

public interface JoinUseCase {
    void join(JoinCommand command);
}
