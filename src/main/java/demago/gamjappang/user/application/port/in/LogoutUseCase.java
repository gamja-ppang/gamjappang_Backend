package demago.gamjappang.user.application.port.in;

import demago.gamjappang.user.application.port.in.command.LogoutCommand;

public interface LogoutUseCase {
    void logout(LogoutCommand command);
}
