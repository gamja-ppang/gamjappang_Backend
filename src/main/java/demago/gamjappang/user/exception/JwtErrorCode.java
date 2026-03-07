package demago.gamjappang.user.exception;

import demago.gamjappang.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum JwtErrorCode implements ErrorCode {

    // 400
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "JWT_400", "refreshToken 누락/형식이 잘못되었습니다."),

    // 401
    INVALID_OR_EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "JWT_401", "refreshToken 만료/위조/서버 저장값과 불일치합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    JwtErrorCode(HttpStatus status, String code, String message) {
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
