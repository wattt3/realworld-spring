package wattt3.realworld.article.domain.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wattt3.realworld.article.domain.Article;
import wattt3.realworld.article.domain.condition.ArticleSearchCondition;

public interface ArticleRepository {

    Article save(Article article);

    Article getBySlug(String slug);

    void delete(Article article);

    Page<Article> findByAuthorIds(List<Long> authorIds, Pageable pageable);

    Page<Article> search(ArticleSearchCondition condition, Pageable pageable);
}
