package demago.gamjappang.user.infrastructure.adapter.out.redis;

import java.security.SecureRandom;
import java.time.Duration;

import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.user.applicationcore.port.out.VerificationCodePort;
import demago.gamjappang.user.exception.UserErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisVerificationCodeAdapter implements VerificationCodePort {

    private static final String PREFIX = "verification_code:";
    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    private final SecureRandom random = new SecureRandom();
    private final StringRedisTemplate redis;
    private final long ttlSeconds;

    public RedisVerificationCodeAdapter(StringRedisTemplate redis,
                                        @Value("${app.verification.ttl-seconds:500}") long ttlSeconds) {
        this.redis = redis;
        this.ttlSeconds = ttlSeconds;
    }

    @Override
    public String issue(String email) {
        String code = randomCode(6);
        redis.opsForValue().set(PREFIX + email, code, Duration.ofSeconds(ttlSeconds));
        return code;
    }

    @Override
    public void verify(String email, String code) {
        String saved = redis.opsForValue().get(PREFIX + email);
        if (saved == null || !saved.equalsIgnoreCase(code)) {
            throw new GamjaException(UserErrorCode.INVALID_VERIFICATION_CODE);
        }
        redis.delete(PREFIX + email);
    }

    private String randomCode(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}

