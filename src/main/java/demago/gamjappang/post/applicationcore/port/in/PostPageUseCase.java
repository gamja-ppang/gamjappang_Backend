package demago.gamjappang.post.applicationcore.port.in;

import demago.gamjappang.post.applicationcore.port.in.command.PostPageCommand;
import demago.gamjappang.post.applicationcore.port.in.result.PostPageResult;

public interface PostPageUseCase {
    PostPageResult getPostPage(PostPageCommand command);
}
