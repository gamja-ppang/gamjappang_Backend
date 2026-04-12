package demago.gamjappang.domain.comment.domain.model;

import demago.gamjappang.domain.post.domain.model.Post;
import demago.gamjappang.domain.user.domain.model.User;

import java.time.LocalDateTime;

public class comment {

    private final Long id;
    private final Post post;
    private final User user;
    private final String content;

    private final LocalDateTime createdAt;

    public comment(Long id, Post post, User user, String content, LocalDateTime createdAt) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
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
