package demago.gamjappang.domain.comment.infrastructure.adepter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataCommentRepository extends JpaRepository<CommentJpaEntity, Long> {
}
