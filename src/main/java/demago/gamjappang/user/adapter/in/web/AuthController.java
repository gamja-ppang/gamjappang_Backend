package demago.gamjappang.user.adapter.in.web;

import demago.gamjappang.global.security.userdetails.UserPrincipal;
import demago.gamjappang.user.adapter.in.web.dto.*;
import demago.gamjappang.user.application.port.in.*;
import demago.gamjappang.user.application.port.in.command.LogoutCommand;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JoinUseCase joinUseCase;
    private final SendVerificationCodeUseCase sendVerificationCodeUseCase;
    private final VerifyEmailUseCase verifyEmailUseCase;
    private final LoginUseCase loginUseCase;
    private final SocialLoginUseCase socialLoginUseCase;
    private final RefreshUseCase refreshUseCase;
    private final LogoutUseCase logoutUseCase;

    public AuthController(
            JoinUseCase joinUseCase,
            SendVerificationCodeUseCase sendVerificationCodeUseCase,
            VerifyEmailUseCase verifyEmailUseCase,
            LoginUseCase loginUseCase,
            SocialLoginUseCase socialLoginUseCase,
            RefreshUseCase refreshUseCase,
            LogoutUseCase logoutUseCase
    ) {
        this.joinUseCase = joinUseCase;
        this.sendVerificationCodeUseCase = sendVerificationCodeUseCase;
        this.verifyEmailUseCase = verifyEmailUseCase;
        this.loginUseCase = loginUseCase;
        this.socialLoginUseCase = socialLoginUseCase;
        this.refreshUseCase = refreshUseCase;
        this.logoutUseCase = logoutUseCase;
    }

    @PostMapping("/join")
    public ResponseEntity<Void> join(@Valid @RequestBody JoinRequest request) {
        joinUseCase.join(request.toCommand());
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/code/send")
    public ResponseEntity<Void> sendVerificationCode(@Valid @RequestBody SendRequest request) {
        sendVerificationCodeUseCase.send(request.toCommand());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/code/verify")
    public ResponseEntity<Void> verify(@Valid @RequestBody VerifyRequest request) {
        verifyEmailUseCase.verify(request.toCommand());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(TokenResponse.from(loginUseCase.login(request.toCommand())));
    }

    @PostMapping("/social")
    public ResponseEntity<TokenResponse> social(@Valid @RequestBody SocialLoginRequest request) {
        return ResponseEntity.ok(TokenResponse.from(socialLoginUseCase.socialLogin(request.toCommand())));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        return ResponseEntity.ok(TokenResponse.from(refreshUseCase.refresh(request.toCommand())));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody LogoutRequest request
    ) {
        logoutUseCase.logout(new LogoutCommand(principal.getId(), request.refreshToken()));
        return ResponseEntity.noContent().build();
    }
}
