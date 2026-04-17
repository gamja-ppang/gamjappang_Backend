package demago.gamjappang.domain.comment.applicationcore.service;

import demago.gamjappang.domain.comment.applicationcore.port.in.CreateCommentUseCase;
import demago.gamjappang.domain.comment.applicationcore.port.in.command.CreateCommentCommand;
import demago.gamjappang.domain.comment.applicationcore.port.out.CommentRepositoryPort;
import demago.gamjappang.domain.comment.domain.model.Comment;
import demago.gamjappang.domain.post.applicationcore.port.out.PostRepositoryPort;
import demago.gamjappang.domain.post.domain.model.Post;
import demago.gamjappang.domain.post.exception.PostErrorCode;
import demago.gamjappang.domain.user.applicationcore.port.out.UserRepositoryPort;
import demago.gamjappang.domain.user.domain.model.User;
import demago.gamjappang.domain.user.exception.UserErrorCode;
import demago.gamjappang.global.error.exception.GamjaException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class CommentApplicationService implements
        CreateCommentUseCase {

    private final CommentRepositoryPort commentRepositoryPort;
    private final PostRepositoryPort postRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    public CommentApplicationService(CommentRepositoryPort commentRepositoryPort, PostRepositoryPort postRepositoryPort, UserRepositoryPort userRepositoryPort) {
        this.commentRepositoryPort = commentRepositoryPort;
        this.postRepositoryPort = postRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public void createComment(CreateCommentCommand command) {
        Post post = postRepositoryPort.findById(command.postId())
                .orElseThrow(() -> new GamjaException(PostErrorCode.POST_NOT_FOUND));

        User user = userRepositoryPort.findById(command.userId())
                .orElseThrow(() -> new GamjaException(UserErrorCode.USER_NOT_FOUND));

        Comment comment = Comment.create(
                post,
                user,
                command.content()
        );

        commentRepositoryPort.save(comment);
    }
}
