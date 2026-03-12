package demago.gamjappang.global.config;

import tools.jackson.databind.ObjectMapper;
import demago.gamjappang.global.config.filter.JwtAuthenticationFilter;
import demago.gamjappang.global.error.ErrorResponse;
import demago.gamjappang.global.error.GlobalErrorCode;
import demago.gamjappang.global.security.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(ObjectMapper objectMapper, JwtAuthenticationFilter jwtFilter) {
        this.objectMapper = objectMapper;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/join",
                                "/api/v1/auth/code/**",
                                "/api/v1/auth/login",
                                "/api/v1/auth/social",
                                "/api/v1/auth/refresh"
                        ).permitAll()

                        .requestMatchers(
                                "/api/v1/management/**"
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) ->
                                writeError(req, res, GlobalErrorCode.UNAUTHORIZED)
                        )
                        .accessDeniedHandler((req, res, e) ->
                                writeError(req, res, GlobalErrorCode.FORBIDDEN)
                        )
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) // H2 console
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(hb -> hb.disable());

        return http.build();
    }

    private void writeError(HttpServletRequest req, HttpServletResponse res, GlobalErrorCode errorCode) throws IOException {
        var body = ErrorResponse.of(errorCode, req.getRequestURI());

        res.setStatus(errorCode.getStatus().value());
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(res.getWriter(), body);
    }
}