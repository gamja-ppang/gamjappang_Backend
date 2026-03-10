package demago.gamjappang.user.applicationcore.port.out;

import demago.gamjappang.global.security.userdetails.UserPrincipal;
import demago.gamjappang.user.domain.model.User;

public interface TokenPort {
    String createAccessToken(User user);
    String createRefreshToken(User user);
    void validate(String token);
    Long getUserId(String token);
    UserPrincipal getPrincipal(String token);
}
