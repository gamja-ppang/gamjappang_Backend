package demago.gamjappang.domain.user.presentation;

import demago.gamjappang.domain.user.presentation.dto.*;
import demago.gamjappang.domain.user.service.AuthService;
import demago.gamjappang.global.security.userdetails.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/join")
    public ResponseEntity<Void> join(@Valid @RequestBody JoinRequest req) {
        authService.join(req.getUsername(), req.getEmail(), req.getPassword());
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verify(@Valid @RequestBody VerifyRequest req) {
        authService.verifyEmail(req.getEmail(), req.getSignupCode());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req.getEmail(), req.getPassword()));
    }

    @PostMapping("/social")
    public ResponseEntity<TokenResponse> social(@Valid @RequestBody SocialLoginRequest req) {
        return ResponseEntity.ok(authService.socialLogin(req.getProvider(), req.getAuthorizationCode(), req.getRedirectUri()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody RefreshRequest req) {
        return ResponseEntity.ok(authService.refresh(req.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserPrincipal principal,
                                       @Valid @RequestBody LogoutRequest req) {
        authService.logout(principal, req.getRefreshToken());
        return ResponseEntity.noContent().build();
    }
}
