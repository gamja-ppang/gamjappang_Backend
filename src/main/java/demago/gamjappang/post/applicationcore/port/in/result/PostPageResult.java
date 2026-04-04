package demago.gamjappang.post.applicationcore.port.in.result;

import java.util.List;

public record PostPageResult(
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