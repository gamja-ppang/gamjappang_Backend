package demago.gamjappang.domain.infrastructure.mail;

import java.security.SecureRandom;
import java.time.Duration;

import demago.gamjappang.domain.user.exception.UserErrorCode;
import demago.gamjappang.global.error.exception.GamjaException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SignupCodeService {

    private static final String PREFIX = "verification_code:"; // key: verification_code:{email}
    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // 헷갈리는 문자 제거
    private final SecureRandom random = new SecureRandom();

    private final StringRedisTemplate redis;
    private final long ttlSeconds;

    public SignupCodeService(StringRedisTemplate redis,
                             @Value("${app.verification.ttl-seconds:500}") long ttlSeconds) {
        this.redis = redis;
        this.ttlSeconds = ttlSeconds;
    }

    public String issue(String email) {
        String code = randomCode(6);
        redis.opsForValue().set(PREFIX + email, code, Duration.ofSeconds(ttlSeconds));
        return code;
    }

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
