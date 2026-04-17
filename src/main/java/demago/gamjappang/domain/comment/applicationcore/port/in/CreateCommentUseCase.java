package demago.gamjappang.domain.comment.applicationcore.port.in;

import demago.gamjappang.domain.comment.applicationcore.port.in.command.CreateCommentCommand;

public interface CreateCommentUseCase {
    void createComment(CreateCommentCommand command);
}
