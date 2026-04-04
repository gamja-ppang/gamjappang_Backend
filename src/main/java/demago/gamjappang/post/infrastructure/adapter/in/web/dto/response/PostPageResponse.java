package demago.gamjappang.post.infrastructure.adapter.in.web.dto.response;

import demago.gamjappang.post.applicationcore.port.in.command.PostPageCommand;
import demago.gamjappang.post.applicationcore.port.in.result.PostPageResult;
import org.springframework.security.core.parameters.P;

import java.util.List;

public record PostPageResponse(
        List<PostSummary> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {
    public record PostSummary(
            Long id,
            String title,
            Author author,
            String summation,
            List<String> tags,
            int viewCount,
            int likeCount,
            int commentCount
    ) {
        public record Author(
                Long id,
                String name
        ) {
        }
    }
}