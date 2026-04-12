package demago.gamjappang.domain.user.applicationcore.port.in;

import demago.gamjappang.domain.user.applicationcore.port.in.command.SocialLoginCommand;
import demago.gamjappang.domain.user.applicationcore.port.in.result.TokenResult;

public interface SocialLoginUseCase {
    TokenResult socialLogin(SocialLoginCommand command);
}
