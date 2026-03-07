package demago.gamjappang.user.adapter.out.redis;

import java.time.Duration;

import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.user.application.port.out.RefreshTokenPort;
import demago.gamjappang.user.exception.JwtErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RedisRefreshTokenAdapter implements RefreshTokenPort {

    private static final String PREFIX = "refresh:";

    private final StringRedisTemplate redis;
    private final long ttlSeconds;

    public RedisRefreshTokenAdapter(StringRedisTemplate redis,
                                    @Value("${security.jwt.refresh-expiration-seconds:1209600}") long ttlSeconds) {
        this.redis = redis;
        this.ttlSeconds = ttlSeconds;
    }

    @Override
    public void save(Long userId, String refreshToken) {
        redis.opsForValue().set(PREFIX + userId, refreshToken, Duration.ofSeconds(ttlSeconds));
    }

    @Override
    public void validateMatches(Long userId, String refreshToken) {
        String saved = redis.opsForValue().get(PREFIX + userId);
        if (saved == null || !saved.equals(refreshToken)) {
            throw new GamjaException(JwtErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    @Override
    public void delete(Long userId) {
        redis.delete(PREFIX + userId);
    }
}
