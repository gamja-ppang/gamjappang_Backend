package demago.gamjappang.domain.user.applicationcore.port.in;

import demago.gamjappang.domain.user.applicationcore.port.in.command.LogoutCommand;

public interface LogoutUseCase {
    void logout(LogoutCommand command);
}
