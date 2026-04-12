package demago.gamjappang.domain.user.applicationcore.port.in;

import demago.gamjappang.domain.user.applicationcore.port.in.command.VerifyEmailCommand;

public interface VerifyEmailUseCase {
    void verify(VerifyEmailCommand command);
}
