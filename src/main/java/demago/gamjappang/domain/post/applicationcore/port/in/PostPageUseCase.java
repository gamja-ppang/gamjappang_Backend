package demago.gamjappang.domain.post.applicationcore.port.in;

import demago.gamjappang.domain.post.applicationcore.port.in.command.PostPageCommand;
import demago.gamjappang.domain.post.applicationcore.port.in.result.PostPageResult;

public interface PostPageUseCase {
    PostPageResult getPostPage(PostPageCommand command);
}
