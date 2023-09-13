package wattt3.realworld.article.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Article {

    private final String slug;
    private final String title;
    private final String description;
    private final String body;
    private final List<Tag> tagList;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final List<Favorite> favorited;
    private final Long authorId;
    private Long id;

    public Article(
            String slug,
            String title,
            String description,
            String body,
            List<Tag> tagList,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            List<Favorite> favorited,
            Long authorId) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.tagList = tagList;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.favorited = favorited;
        this.authorId = authorId;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBody() {
        return body;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<Favorite> getFavorited() {
        return favorited;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
