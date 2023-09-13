package wattt3.realworld.article.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleRepository {

    private final Map<Long, Article> articles = new HashMap<>();
    private Long sequence = 1L;

    public void save(Article article) {
        article.setId(sequence);
        ++sequence;
        articles.put(article.getId(), article);
    }

    public List<Article> findAll() {
        return new ArrayList<>(articles.values());
    }
}
