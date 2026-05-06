package demago.gamjappang.domain.comment.domain.model;

import demago.gamjappang.domain.post.domain.model.Post;
import demago.gamjappang.domain.user.domain.model.User;
import demago.gamjappang.global.error.GlobalErrorCode;
import demago.gamjappang.global.error.exception.GamjaException;

import java.time.LocalDateTime;

public class Comment {

    private final Long id;
    private final Post post;
    private final User user;
    private final String content;

    private final LocalDateTime createdAt;

    public Comment(Long id, Post post, User user, String content, LocalDateTime createdAt) {
        validateUser(user);
        validatePost(post);
        validateContent(content);

        this.id = id;
        this.post = post;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static Comment create(Post post, User user, String content) {
        LocalDateTime now = LocalDateTime.now();
        return new Comment(null, post, user, content, now);
    }

    private static void validateUser(User user) {
        if (user == null) {
            throw new GamjaException(GlobalErrorCode.INVALID_REQUEST);
        }
    }

    private static void validatePost(Post post) {
        if (post == null) {
            throw new GamjaException(GlobalErrorCode.INVALID_REQUEST);
        }
    }

    private static void validateContent(String content) {
        if (content == null || content.isBlank() || content.length() > 250) {
            throw new GamjaException(GlobalErrorCode.INVALID_REQUEST);
        }
    }

    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
