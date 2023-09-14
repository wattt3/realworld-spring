package wattt3.realworld.article.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import wattt3.realworld.article.domain.Article;
import wattt3.realworld.article.domain.Tag;

public record ArticleDTO(String slug,
                         String title,
                         String description,
                         String body,
                         List<String> tagList,
                         LocalDateTime createdAt,
                         LocalDateTime updatedAt,
                         boolean favorited,
                         int favoritesCount,
                         AuthorDTO author) {

    public static ArticleDTO of(Article article, boolean favorited, AuthorDTO author) {
        return new ArticleDTO(article.getSlug(), article.getTitle(), article.getDescription(),
                article.getBody(), article.getTags().stream().map(Tag::getName).toList(),
                article.getCreatedAt(), article.getUpdatedAt(), favorited,
                article.getFavoriteUserIds().size(), author);
    }

}
