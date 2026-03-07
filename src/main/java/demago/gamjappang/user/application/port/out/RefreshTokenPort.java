package demago.gamjappang.user.application.port.out;

public interface RefreshTokenPort {
    void save(Long userId, String refreshToken);
    void validateMatches(Long userId, String refreshToken);
    void delete(Long userId);
}
