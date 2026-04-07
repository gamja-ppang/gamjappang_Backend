package demago.gamjappang.post.infrastructure.adapter.out.persistence;

import demago.gamjappang.post.applicationcore.port.out.PostRepositoryPort;
import demago.gamjappang.post.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataPostJpaRepository extends JpaRepository<PostJpaEntity, Long> {

    void delete(Long id);

    @Query(
            value = """
                    select distinct p
                    from PostJpaEntity p
                    left join p.tags t
                    where (:tag is null or t = :tag)
                      and (
                            :keyword is null
                            or p.title like concat('%', :keyword, '%')
                            or p.content like concat('%', :keyword, '%')
                          )
                    """,
            countQuery = """
                    select count(distinct p)
                    from PostJpaEntity p
                    left join p.tags t
                    where (:tag is null or t = :tag)
                      and (
                            :keyword is null
                            or p.title like concat('%', :keyword, '%')
                            or p.content like concat('%', :keyword, '%')
                          )
                    """
    )
    Page<PostJpaEntity> searchPosts(
            @Param("tag") String tag,
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
