package demago.gamjappang.global.security.jwt;

import demago.gamjappang.user.adapter.out.persistence.UserJpaEntity;
import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.user.application.port.out.TokenPort;
import demago.gamjappang.user.domain.model.Role;
import demago.gamjappang.user.domain.model.User;
import demago.gamjappang.user.exception.JwtErrorCode;
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
public class JwtTokenProvider implements TokenPort {

    private final JwtProperties props;
    private final Key key;

    public JwtTokenProvider(JwtProperties props) {
        this.props = props;
        this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    @Override
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

    @Override
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

    @Override
    public void validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new GamjaException(JwtErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    @Override
    public Long getUserId(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return Long.parseLong(claims.getSubject());
        } catch (JwtException | IllegalArgumentException e) {
            throw new GamjaException(JwtErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    @Override
    public UserPrincipal getPrincipal(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String type = claims.get("type", String.class);
            if (!"access".equals(type)) {
                throw new GamjaException(JwtErrorCode.INVALID_REFRESH_TOKEN);
            }

            Long id = Long.parseLong(claims.getSubject());
            String email = claims.get("email", String.class);
            String roleValue = claims.get("role", String.class);
            Role role = Role.valueOf(roleValue);

            return new UserPrincipal(
                    id,
                    email,
                    null,
                    role,
                    true,
                    false
            );
        } catch (JwtException | IllegalArgumentException e) {
            throw new GamjaException(JwtErrorCode.INVALID_REFRESH_TOKEN);
        }
    }
}