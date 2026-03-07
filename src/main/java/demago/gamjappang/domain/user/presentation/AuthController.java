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
        authService.join(req.username(), req.email(), req.password());
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/code/send")
    public ResponseEntity<Void> sendVerificationCode(@Valid @RequestBody SendRequset req) {
        authService.sendVerificationCode(req.email());
        return ResponseEntity.status(204).build();
    }

    @PostMapping("/code/verify")
    public ResponseEntity<Void> verify(@Valid @RequestBody VerifyRequest req) {
        authService.verifyEmail(req.email(), req.signupCode());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req.email(), req.password()));
    }

    @PostMapping("/social")
    public ResponseEntity<TokenResponse> social(@Valid @RequestBody SocialLoginRequest req) {
        return ResponseEntity.ok(authService.socialLogin(req.provider(), req.authorizationCode(), req.redirectUri()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody RefreshRequest req) {
        return ResponseEntity.ok(authService.refresh(req.refreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserPrincipal principal,
                                       @Valid @RequestBody LogoutRequest req) {
        authService.logout(principal, req.refreshToken());
        return ResponseEntity.noContent().build();
    }
}
