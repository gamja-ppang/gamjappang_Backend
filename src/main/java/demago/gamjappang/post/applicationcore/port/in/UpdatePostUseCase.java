package demago.gamjappang.post.applicationcore.port.in;

import demago.gamjappang.post.applicationcore.port.in.command.UpdatePostCommand;
import demago.gamjappang.post.applicationcore.port.in.result.UpdatePostResult;

public interface UpdatePostUseCase {
    UpdatePostResult updatePost(UpdatePostCommand command);
}
