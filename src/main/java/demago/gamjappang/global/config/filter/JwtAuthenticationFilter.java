package demago.gamjappang.global.config.filter;

import demago.gamjappang.global.error.ErrorResponse;
import demago.gamjappang.global.error.GlobalErrorCode;
import demago.gamjappang.global.security.jwt.JwtTokenProvider;
import demago.gamjappang.global.security.userdetails.UserPrincipal;
import org.springframework.http.MediaType;
import tools.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, ObjectMapper objectMapper) {
        this.tokenProvider = tokenProvider;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                tokenProvider.validate(token);
                UserPrincipal principal = tokenProvider.getPrincipal(token);

                var auth = new UsernamePasswordAuthenticationToken(
                        principal, null, principal.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (RuntimeException ex) {
                SecurityContextHolder.clearContext();
                writeError(request, response, GlobalErrorCode.UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void writeError(HttpServletRequest req, HttpServletResponse res, GlobalErrorCode errorCode) throws IOException {
        var body = ErrorResponse.of(errorCode, req.getRequestURI());

        res.setStatus(errorCode.getStatus().value());
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(res.getWriter(), body);
    }
}