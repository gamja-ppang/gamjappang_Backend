package demago.gamjappang.domain.post.applicationcore.port.in;

import demago.gamjappang.domain.post.applicationcore.port.in.command.UpdatePostCommand;
import demago.gamjappang.domain.post.applicationcore.port.in.result.UpdatePostResult;

public interface UpdatePostUseCase {
    UpdatePostResult updatePost(UpdatePostCommand command);
}
