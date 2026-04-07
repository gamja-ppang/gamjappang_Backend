package demago.gamjappang.post.infrastructure.adapter.in.web.dto.response;

import demago.gamjappang.post.applicationcore.port.in.result.PostPageResult;

import java.util.List;

public record PostPageResponse(
        List<PostSummary> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {
    public static PostPageResponse from(PostPageResult result) {
        return new PostPageResponse(
                result.content().stream()
                        .map(PostSummary::from)
                        .toList(),
                result.page(),
                result.size(),
                result.totalElements(),
                result.totalPages(),
                result.hasNext()
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

        public static PostSummary from(PostPageResult.PostSummary result) {
            return new PostSummary(
                    result.id(),
                    result.title(),
                    Author.from(result.author()),
                    result.summation(),
                    result.tags(),
                    result.viewCount(),
                    result.likeCount(),
                    result.commentCount()
            );
        }

        public record Author(
                Long id,
                String name
        ) {
            public static Author from(PostPageResult.PostSummary.Author result) {
                return new Author(
                        result.id(),
                        result.name()
                );
            }
        }
    }
}