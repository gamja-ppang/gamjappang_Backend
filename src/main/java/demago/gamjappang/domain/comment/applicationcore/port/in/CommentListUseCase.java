package demago.gamjappang.domain.comment.applicationcore.port.in;

import demago.gamjappang.domain.comment.applicationcore.port.in.command.CommentListCommand;
import demago.gamjappang.domain.comment.applicationcore.port.in.result.CommentListResult;

public interface CommentListUseCase {
    CommentListResult getCommentList(CommentListCommand command);
}
