package demago.gamjappang.domain.post.applicationcore.port.in;

import demago.gamjappang.domain.post.applicationcore.port.in.command.DeletePostCommand;

public interface DeletePostUseCase {
    void deletePost(DeletePostCommand command);
}
