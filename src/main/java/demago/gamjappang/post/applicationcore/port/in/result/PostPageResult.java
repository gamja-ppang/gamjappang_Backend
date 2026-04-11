package demago.gamjappang.post.applicationcore.port.in.result;

import demago.gamjappang.post.domain.model.Post;
import demago.gamjappang.post.infrastructure.adapter.in.web.dto.response.common.Author;
import org.springframework.data.domain.Page;

import java.util.List;

public record PostPageResult(
        List<PostSummary> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {

    public static PostPageResult from(Page<Post> postPage) {
        return new PostPageResult(
                postPage.getContent().stream()
                        .map(PostSummary::from)
                        .toList(),
                postPage.getNumber(),
                postPage.getSize(),
                postPage.getTotalElements(),
                postPage.getTotalPages(),
                postPage.hasNext()
        );
    }

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

        public static PostSummary from(Post post) {
            return new PostSummary(
                    post.getId(),
                    post.getTitle(),
                    Author.from(post),
                    makeSummation(post.getContent()),
                    post.getTags(),
                    post.getViewCount(),
                    post.getHeartCount(),
                    post.getCommentCount()
            );
        }

        private static String makeSummation(String content) {
            if (content == null || content.isBlank()) {
                return "";
            }

            String normalized = content.replaceAll("\\s+", " ").trim();
            int maxLength = 100;

            if (normalized.length() <= maxLength) {
                return normalized;
            }

            return normalized.substring(0, maxLength) + "...";
        }
    }
}