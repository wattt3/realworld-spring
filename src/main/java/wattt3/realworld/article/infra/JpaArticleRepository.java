package wattt3.realworld.article.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import wattt3.realworld.article.domain.Article;

public interface JpaArticleRepository extends JpaRepository<Article, Long> {

}
