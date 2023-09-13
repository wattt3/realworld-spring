package wattt3.realworld.article.infra;

import java.util.List;
import org.springframework.stereotype.Repository;
import wattt3.realworld.article.domain.Article;
import wattt3.realworld.article.domain.repository.ArticleRepository;

@Repository
public class ArticleRepositoryAdapter implements ArticleRepository {

    private final JpaArticleRepository jpaArticleRepository;

    public ArticleRepositoryAdapter(JpaArticleRepository jpaArticleRepository) {
        this.jpaArticleRepository = jpaArticleRepository;
    }

    @Override
    public Article save(Article article) {
        return jpaArticleRepository.save(article);
    }

    @Override
    public List<Article> findAll() {
        return jpaArticleRepository.findAll();
    }

    @Override
    public Article getBySlug(String slug) {
        return jpaArticleRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException(
                        "slug: %s 가 존재하지 않습니다.".formatted(slug)));
    }
}
