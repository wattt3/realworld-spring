package wattt3.realworld.article.infra;

import java.util.Collection;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wattt3.realworld.article.domain.Article;

public interface JpaArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findBySlug(String slug);

    Page<Article> findByAuthorIdIn(Collection<Long> authorIds, Pageable pageable);

}
