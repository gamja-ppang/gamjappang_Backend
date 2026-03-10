package demago.gamjappang.user.applicationcore.port.in;

import demago.gamjappang.user.applicationcore.port.in.command.VerifyEmailCommand;

public interface VerifyEmailUseCase {
    void verify(VerifyEmailCommand command);
}
