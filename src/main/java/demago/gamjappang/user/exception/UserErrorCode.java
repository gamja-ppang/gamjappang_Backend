package demago.gamjappang.user.exception;

import demago.gamjappang.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {

    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "USR_400", "인증 코드가 올바르지 않습니다."),

    // 401/403
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "USR_401", "비밀번호가 올바르지 않습니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.FORBIDDEN, "USR_403_01", "이메일 인증이 필요합니다."),
    USER_BLOCKED(HttpStatus.FORBIDDEN, "USR_403_02", "차단된 유저입니다."),

    // 404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USR_404_01", "존재하지 않는 이름입니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "USR_404_02", "존재하지 않는 이메일입니다."),

    // 409
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "USR_409", "이미 가입된 이메일입니다."),

    // 503
    EMAIL_SEND_FAILED(HttpStatus.SERVICE_UNAVAILABLE, "USR_503", "이메일 전송에 실패하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    UserErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
