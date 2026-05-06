package demago.gamjappang.domain.comment.applicationcore.port.in;

import demago.gamjappang.domain.comment.applicationcore.port.in.command.DeleteCommentCommand;

public interface DeleteCommentUseCase {
    void deleteComment(DeleteCommentCommand command);
}
