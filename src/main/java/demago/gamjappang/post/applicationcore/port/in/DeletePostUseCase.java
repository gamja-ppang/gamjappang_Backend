package demago.gamjappang.post.applicationcore.port.in;

import demago.gamjappang.post.applicationcore.port.in.command.DeletePostCommand;

public interface DeletePostUseCase {
    void deletePost(DeletePostCommand command);
}
