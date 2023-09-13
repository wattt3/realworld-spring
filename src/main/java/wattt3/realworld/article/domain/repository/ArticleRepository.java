package wattt3.realworld.article.domain.repository;

import java.util.List;
import wattt3.realworld.article.domain.Article;

public interface ArticleRepository {

    Article save(Article article);

    List<Article> findAll();

    Article getBySlug(String slug);
}
