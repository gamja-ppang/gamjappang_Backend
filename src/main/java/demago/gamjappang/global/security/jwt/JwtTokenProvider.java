package demago.gamjappang.global.security.jwt;

import demago.gamjappang.domain.user.entity.User;
import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.global.security.jwt.exception.JwtErrorCode;
import demago.gamjappang.global.security.userdetails.UserPrincipal;
import demago.gamjappang.global.security.userdetails.UserPrincipalService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final JwtProperties props;
    private final UserPrincipalService principalService;
    private final Key key;

    public JwtTokenProvider(JwtProperties props, UserPrincipalService principalService) {
        this.props = props;
        this.principalService = principalService;
        this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(User user) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(props.getAccessExpirationSeconds());
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .claim("type", "access")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(User user) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(props.getRefreshExpirationSeconds());
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("type", "refresh")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public void validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new GamjaException(JwtErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    public Long getUserId(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody();
            return Long.parseLong(claims.getSubject());
        } catch (JwtException | IllegalArgumentException e) {
            throw new GamjaException(JwtErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    public UserPrincipal getPrincipal(String token) {
        Long userId = getUserId(token);
        return principalService.loadById(userId);
    }
}
