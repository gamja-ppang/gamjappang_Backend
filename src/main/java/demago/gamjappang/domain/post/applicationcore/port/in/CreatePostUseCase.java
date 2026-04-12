package demago.gamjappang.domain.post.applicationcore.port.in;

import demago.gamjappang.domain.post.applicationcore.port.in.command.CreatePostCommand;
import demago.gamjappang.domain.post.applicationcore.port.in.result.CreatePostResult;

public interface CreatePostUseCase {
    CreatePostResult createPost(CreatePostCommand command);
}
