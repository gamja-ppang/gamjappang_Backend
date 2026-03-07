package demago.gamjappang.user.application.port.in;

import demago.gamjappang.user.application.port.in.command.SendVerificationCodeCommand;

public interface SendVerificationCodeUseCase {
    void send(SendVerificationCodeCommand command);
}
