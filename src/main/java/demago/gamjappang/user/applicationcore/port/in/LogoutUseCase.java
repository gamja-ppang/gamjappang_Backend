package demago.gamjappang.user.applicationcore.port.in;

import demago.gamjappang.user.applicationcore.port.in.command.LogoutCommand;

public interface LogoutUseCase {
    void logout(LogoutCommand command);
}
