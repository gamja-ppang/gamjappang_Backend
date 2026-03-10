package demago.gamjappang.user.application.port.in;

import demago.gamjappang.user.application.port.in.command.JoinCommand;

public interface JoinUseCase {
    void join(JoinCommand command);
}
