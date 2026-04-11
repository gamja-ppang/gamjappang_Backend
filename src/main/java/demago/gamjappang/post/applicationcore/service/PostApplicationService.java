package demago.gamjappang.post.applicationcore.service;

import demago.gamjappang.global.error.GlobalErrorCode;
import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.post.applicationcore.port.in.*;
import demago.gamjappang.post.applicationcore.port.in.command.*;
import demago.gamjappang.post.applicationcore.port.in.result.CreatePostResult;
import demago.gamjappang.post.applicationcore.port.in.result.GetPostResult;
import demago.gamjappang.post.applicationcore.port.in.result.PostPageResult;
import demago.gamjappang.post.applicationcore.port.in.result.UpdatePostResult;
import demago.gamjappang.post.applicationcore.port.in.result.common.Author;
import demago.gamjappang.post.applicationcore.port.out.PostRepositoryPort;
import demago.gamjappang.post.domain.model.Post;
import demago.gamjappang.post.exception.PostErrorCode;
import demago.gamjappang.user.applicationcore.port.out.UserRepositoryPort;
import demago.gamjappang.user.domain.model.User;
import demago.gamjappang.user.exception.UserErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Transactional
public class PostApplicationService implements
        CreatePostUseCase,
        UpdatePostUseCase,
        DeletePostUseCase,
        PostPageUseCase,
        GetPostUseCase {

    private final PostRepositoryPort postRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;


    public PostApplicationService(PostRepositoryPort postRepositoryPort, UserRepositoryPort userRepositoryPort) {
        this.postRepositoryPort = postRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public CreatePostResult createPost(CreatePostCommand command) {
        User author = userRepositoryPort.findById(command.userId())
                .orElseThrow(() -> new GamjaException(UserErrorCode.USER_NOT_FOUND));

        Post post = Post.create(
                author,
                command.title(),
                command.content(),
                command.tags()
        );

        Post savedPost = postRepositoryPort.save(post);

        return new CreatePostResult(
               savedPost.getId(),
               new Author(savedPost.getUser().getId(), savedPost.getUser().getUsername()),
               savedPost.getTitle(),
               savedPost.getContent(),
               savedPost.getTags(),
               savedPost.getCreatedAt(),
               savedPost.getUpdatedAt()
        );
    }

    @Override
    public UpdatePostResult updatePost(UpdatePostCommand command) {
        User auther = userRepositoryPort.findById(command.userId())
                .orElseThrow(() -> new GamjaException(UserErrorCode.USER_NOT_FOUND)); // 짜피 걸릴 일 없음

        Post post = postRepositoryPort.findById(command.postId())
                .orElseThrow(() -> new GamjaException(PostErrorCode.POST_NOT_FOUND));

        if (!Objects.equals(post.getUser().getId(), auther.getId())) {
            throw new GamjaException(GlobalErrorCode.FORBIDDEN);
        }

        Post newPost = Post.restore(
                post.getId(),
                post.getUser(),
                command.title(),
                command.content(),
                command.tags(),
                post.getViewCount(),
                post.getHeartCount(),
                post.getCommentCount(),
                post.getCreatedAt(),
                LocalDateTime.now()
        );

        Post updatedPost = postRepositoryPort.update(newPost);

        return new UpdatePostResult(
                updatedPost.getId(),
                new Author(updatedPost.getUser().getId(), updatedPost.getUser().getUsername()),
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getTags(),
                updatedPost.getViewCount(),
                updatedPost.getHeartCount(),
                updatedPost.getCommentCount(),
                updatedPost.getCreatedAt(),
                updatedPost.getUpdatedAt()
        );
    }

    @Override
    public void deletePost(DeletePostCommand command) {
        User auther = userRepositoryPort.findById(command.userId())
                .orElseThrow(() -> new GamjaException(UserErrorCode.USER_NOT_FOUND)); // 짜피 걸릴 일 없음

        Post post = postRepositoryPort.findById(command.postId())
                .orElseThrow(() -> new GamjaException(PostErrorCode.POST_NOT_FOUND));

        if (!Objects.equals(post.getUser().getId(), auther.getId())) {
            throw new GamjaException(GlobalErrorCode.FORBIDDEN);
        }

        postRepositoryPort.delete(command.postId());
    }

    @Override
    public PostPageResult getPostPage(PostPageCommand command) {
        Page<Post> postList = postRepositoryPort.getPostPage(command.tag(), command.keyword(), command.pageable());

        return PostPageResult.from(postList);
    }

    @Override
    public GetPostResult getPost(GetPostCommand command) {
        Post post = postRepositoryPort.findById(command.postId())
                .orElseThrow(() -> new GamjaException(PostErrorCode.POST_NOT_FOUND));

        return new GetPostResult(
                post.getId(),
                new Author(post.getUser().getId(), post.getUser().getUsername()),
                post.getTitle(),
                post.getContent(),
                post.getTags(),
                post.getViewCount(),
                post.getHeartCount(),
                post.getCommentCount(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
