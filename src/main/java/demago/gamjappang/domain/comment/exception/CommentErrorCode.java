package demago.gamjappang.domain.comment.exception;

import demago.gamjappang.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CommentErrorCode implements ErrorCode {

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CMT_404", "댓글을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

     CommentErrorCode(HttpStatus status, String code, String message) {
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
