package demago.gamjappang.user.application.port.in;

import demago.gamjappang.user.application.port.in.command.VerifyEmailCommand;

public interface VerifyEmailUseCase {
    void verify(VerifyEmailCommand command);
}
