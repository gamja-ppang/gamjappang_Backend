package demago.gamjappang.post.applicationcore.port.in;

import demago.gamjappang.post.applicationcore.port.in.command.GetPostCommand;
import demago.gamjappang.post.applicationcore.port.in.result.GetPostResult;

public interface GetPostUseCase {
    GetPostResult getPost(GetPostCommand command);
}
