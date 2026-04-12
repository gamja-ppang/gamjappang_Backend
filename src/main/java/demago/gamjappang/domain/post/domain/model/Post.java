package demago.gamjappang.domain.post.domain.model;

import demago.gamjappang.global.error.GlobalErrorCode;
import demago.gamjappang.global.error.exception.GamjaException;
import demago.gamjappang.domain.user.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class Post {

    private final Long id;
    private final User user;
    private final String title;
    private final String content;
    private final List<String> tags;
    private final int viewCount;
    private final int commentCount;
    private final int heartCount;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public Post(Long id, User user, String title, String content, List<String> tags, int viewCount, int heartCount, int commentCount,  LocalDateTime createdAt, LocalDateTime updatedAt) {
        validateUser(user);
        validateTitle(title);
        validateContent(content);

        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.viewCount = viewCount;
        this.heartCount = heartCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Post create(User user, String title, String content, List<String> tags) {
        LocalDateTime now = LocalDateTime.now();
        return new Post(null, user, title, content, tags, 0, 0, 0, now, now);
    }

    public static Post restore(Long id, User user, String title, String content, List<String> tags, int viewCount, int heartCount, int commentCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Post(id, user, title, content, tags, viewCount, heartCount, commentCount, createdAt, updatedAt);
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

    public List<String> getTags() {
        return tags;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getHeartCount() {
        return heartCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
