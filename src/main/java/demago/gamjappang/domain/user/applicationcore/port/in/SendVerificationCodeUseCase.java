package demago.gamjappang.domain.user.applicationcore.port.in;

import demago.gamjappang.domain.user.applicationcore.port.in.command.SendVerificationCodeCommand;

public interface SendVerificationCodeUseCase {
    void send(SendVerificationCodeCommand command);
}
