package demago.gamjappang.post.exception;

import demago.gamjappang.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum PostErrorCode implements ErrorCode {

    // 404
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "PST_404", "게시글을 찾을 수 없습니다."),;

    private final HttpStatus status;
    private final String code;
    private final String message;

    PostErrorCode(HttpStatus status, String code, String message) {
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
