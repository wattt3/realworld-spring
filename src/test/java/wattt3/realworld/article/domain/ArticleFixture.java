package wattt3.realworld.article.domain;

import java.util.Collections;
import java.util.List;

public class ArticleFixture {

    private Long id = 1L;
    private String slug = "a-title";
    private String title = "a title";
    private String description = "description";
    private String body = "body";
    private List<Tag> tags = Collections.emptyList();
    private List<Long> favoriteUserIds = Collections.emptyList();
    private Long authorId = 1L;

    public static ArticleFixture aArticle() {
        return new ArticleFixture();
    }

    public ArticleFixture id(Long id) {
        this.id = id;
        return this;
    }

    public ArticleFixture slug(String slug) {
        this.slug = slug;
        return this;
    }

    public ArticleFixture title(String title) {
        this.title = title;
        return this;
    }

    public ArticleFixture description(String description) {
        this.description = description;
        return this;
    }

    public ArticleFixture body(String body) {
        this.body = body;
        return this;
    }

    public ArticleFixture tags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public ArticleFixture favoriteUserIds(List<Long> favoriteUserIds) {
        this.favoriteUserIds = favoriteUserIds;
        return this;
    }

    public ArticleFixture authorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public Article build() {
        return new Article(id, slug, title, description, body, tags, favoriteUserIds, authorId);
    }

}