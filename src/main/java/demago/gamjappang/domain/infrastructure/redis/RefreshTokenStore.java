package demago.gamjappang.domain.infrastructure.redis;

import java.time.Duration;

import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.global.security.jwt.exception.JwtErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenStore {

    private static final String PREFIX = "refresh:"; // key: refresh:{userId}

    private final StringRedisTemplate redis;
    private final long ttlSeconds;

    public RefreshTokenStore(StringRedisTemplate redis,
                             @Value("${security.jwt.refresh-expiration-seconds:1209600}") long ttlSeconds) {
        this.redis = redis;
        this.ttlSeconds = ttlSeconds;
    }

    public void save(Long userId, String refreshToken) {
        redis.opsForValue().set(PREFIX + userId, refreshToken, Duration.ofSeconds(ttlSeconds));
    }

    public void validateMatches(Long userId, String refreshToken) {
        String saved = redis.opsForValue().get(PREFIX + userId);
        if (saved == null || !saved.equals(refreshToken)) {
            throw new GamjaException(JwtErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    public void delete(Long userId) {
        redis.delete(PREFIX + userId);
    }
}
