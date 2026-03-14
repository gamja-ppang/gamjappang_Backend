package demago.gamjappang.post.applicationcore.service;

import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.post.applicationcore.port.in.CreatePostUseCase;
import demago.gamjappang.post.applicationcore.port.in.command.CreatePostCommand;
import demago.gamjappang.post.applicationcore.port.in.result.CreatePostResult;
import demago.gamjappang.post.applicationcore.port.out.PostRepositoryPort;
import demago.gamjappang.post.domain.model.Post;
import demago.gamjappang.user.applicationcore.port.out.UserRepositoryPort;
import demago.gamjappang.user.domain.model.User;
import demago.gamjappang.user.exception.UserErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostApplicationService implements
        CreatePostUseCase {

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

        return postRepositoryPort.save(post);
    }
}
