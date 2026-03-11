package demago.gamjappang.post.domain.model;

import demago.gamjappang.global.error.GlobalErrorCode;
import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.user.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class Post {

    private final Long id;
    private final User user;
    private final String title;
    private final String content;
    private final int viewCount;
    private final List<String> tags;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public Post(Long id, User user, String title, String content, int viewCount, List<String> tags, LocalDateTime createdAt, LocalDateTime updatedAt) {
        validateUser(user);
        validateTitle(title);
        validateContent(content);

        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Post create(User user, String title, String content, List<String> tags, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Post(null, user, title, content, 0, tags, createdAt, updatedAt);
    }

    public static Post restore(Long id, User user, String title, String content, int viewCount, List<String> tags, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Post(id, user, title, content, viewCount, tags, createdAt, updatedAt);
    }

    private static void validateUser(User user) {
        if (user == null) {
            throw new GamjaException(GlobalErrorCode.INVALID_REQUEST);
        }
    }

    public static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new GamjaException(GlobalErrorCode.INVALID_REQUEST);
        }
    }

    public static void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new GamjaException(GlobalErrorCode.INVALID_REQUEST);
        }
    }


    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getViewCount() {
        return viewCount;
    }

    public List<String> getTags() {
        return tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
