package demago.gamjappang.user.applicationcore.port.in;

import demago.gamjappang.user.applicationcore.port.in.command.SendVerificationCodeCommand;

public interface SendVerificationCodeUseCase {
    void send(SendVerificationCodeCommand command);
}
