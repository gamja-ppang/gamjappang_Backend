package demago.gamjappang.post.applicationcore.port.in;

import demago.gamjappang.post.applicationcore.port.in.command.CreatePostCommand;
import demago.gamjappang.post.applicationcore.port.in.result.CreatePostResult;

public interface CreatePostUseCase {
    CreatePostResult createPost(CreatePostCommand command);
}
