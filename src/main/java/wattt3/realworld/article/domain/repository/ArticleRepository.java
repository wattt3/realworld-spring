package wattt3.realworld.article.domain.repository;

import wattt3.realworld.article.domain.Article;

public interface ArticleRepository {

    Article save(Article article);

    Article getBySlug(String slug);

    void delete(Article article);
}
