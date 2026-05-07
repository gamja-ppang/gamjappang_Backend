package demago.gamjappang.domain.comment.applicationcore.service;

import demago.gamjappang.domain.comment.applicationcore.port.in.CommentListUseCase;
import demago.gamjappang.domain.comment.applicationcore.port.in.CreateCommentUseCase;
import demago.gamjappang.domain.comment.applicationcore.port.in.DeleteCommentUseCase;
import demago.gamjappang.domain.comment.applicationcore.port.in.command.CommentListCommand;
import demago.gamjappang.domain.comment.applicationcore.port.in.command.CreateCommentCommand;
import demago.gamjappang.domain.comment.applicationcore.port.in.command.DeleteCommentCommand;
import demago.gamjappang.domain.comment.applicationcore.port.in.result.CommentListResult;
import demago.gamjappang.domain.comment.applicationcore.port.out.CommentRepositoryPort;
import demago.gamjappang.domain.comment.domain.model.Comment;
import demago.gamjappang.domain.comment.exception.CommentErrorCode;
import demago.gamjappang.domain.post.applicationcore.port.out.PostRepositoryPort;
import demago.gamjappang.domain.post.domain.model.Post;
import demago.gamjappang.domain.post.exception.PostErrorCode;
import demago.gamjappang.domain.user.applicationcore.port.out.UserRepositoryPort;
import demago.gamjappang.domain.user.domain.model.User;
import demago.gamjappang.domain.user.exception.UserErrorCode;
import demago.gamjappang.global.error.GlobalErrorCode;
import demago.gamjappang.global.error.exception.GamjaException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@Transactional
public class CommentApplicationService implements
        CreateCommentUseCase,
        CommentListUseCase,
        DeleteCommentUseCase {

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
        updateCommentCount(post, 1);
    }

    @Override
    public CommentListResult getCommentList(CommentListCommand command) {
        Post post = postRepositoryPort.findById(command.postId())
                .orElseThrow(() -> new GamjaException(PostErrorCode.POST_NOT_FOUND));

        return CommentListResult.from(commentRepositoryPort.findByPostId(post.getId()));
    }

    @Override
    public void deleteComment(DeleteCommentCommand command) {
        User user = userRepositoryPort.findById(command.userId())
                .orElseThrow(() -> new GamjaException(UserErrorCode.USER_NOT_FOUND));

        Comment comment = commentRepositoryPort.findById(command.commentId())
                .orElseThrow(() -> new GamjaException(CommentErrorCode.COMMENT_NOT_FOUND));

        if (!Objects.equals(comment.getUser().getId(), user.getId())) {
            throw new GamjaException(GlobalErrorCode.FORBIDDEN);
        }

        commentRepositoryPort.delete(command.commentId());
        updateCommentCount(comment.getPost(), -1);
    }

    private void updateCommentCount(Post post, int delta) {
        int nextCommentCount = Math.max(0, post.getCommentCount() + delta);

        Post updatedPost = Post.restore(
                post.getId(),
                post.getUser(),
                post.getTitle(),
                post.getContent(),
                post.getTags(),
                post.getViewCount(),
                post.getHeartCount(),
                nextCommentCount,
                post.getCreatedAt(),
                post.getUpdatedAt()
        );

        postRepositoryPort.update(updatedPost);
    }
}
