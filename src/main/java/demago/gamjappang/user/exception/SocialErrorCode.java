package demago.gamjappang.user.exception;

import demago.gamjappang.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum SocialErrorCode implements ErrorCode {

    // 400
    OAUTH_BAD_REQUEST(HttpStatus.BAD_REQUEST, "SCL_400_01","provider/code/redirectUri가 누락되었습니다."),
    INVALID_AUTHCODE(HttpStatus.BAD_REQUEST, "SCL_400_02","authorizationCode가 유효하지 않습니다."),

    // 500
    OAUTH_PROVIDER_FAILED(HttpStatus.BAD_GATEWAY, "SCL_502", "provider 통신에 실패하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    SocialErrorCode(HttpStatus status, String code, String message) {
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
