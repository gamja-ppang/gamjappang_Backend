package demago.gamjappang.domain.comment.infrastructure.adepter.out.persistence;

import demago.gamjappang.domain.post.infrastructure.adapter.out.persistence.PostJpaEntity;
import demago.gamjappang.domain.user.infrastructure.adapter.out.persistence.UserJpaEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class CommentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostJpaEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity user;

    @Column(nullable = false, length = 250)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public CommentJpaEntity() {
    }

    public CommentJpaEntity(Long id, PostJpaEntity post, UserJpaEntity user, String content, LocalDateTime createdAt) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public PostJpaEntity getPost(){
        return post;
    }

    public UserJpaEntity getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
