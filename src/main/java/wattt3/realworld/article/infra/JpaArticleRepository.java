package wattt3.realworld.article.infra;

import java.util.Collection;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wattt3.realworld.article.domain.Article;

public interface JpaArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findBySlug(String slug);

    @Query("select a from Article a join fetch a.author where a.author.id in :authorIds")
    Page<Article> findByAuthorIdIn(@Param("authorIds") Collection<Long> authorIds,
            Pageable pageable);

}
