package demago.gamjappang.post.infrastructure.adapter.out.persistence;

import demago.gamjappang.post.applicationcore.port.out.PostRepositoryPort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPostJpaRepository extends JpaRepository<PostJpaEntity, Long> {

}
