package wattt3.realworld.article.application.service;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleDTO(String slug,
                         String title,
                         String description,
                         String body,
                         List<Tag> tagList,
                         LocalDateTime createdAt,
                         LocalDateTime updatedAt,
                         boolean favorited,
                         int favoritesCount,
                         Author author) {

    public static ArticleDTO of(Article article, boolean favorited, Author author) {
        return new ArticleDTO(article.getSlug(), article.getTitle(), article.getDescription(),
                article.getBody(),
                article.getTagList(), article.getCreatedAt(), article.getUpdatedAt(), favorited,
                article.getFavorited().size(), author);
    }

}
