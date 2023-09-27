package wattt3.realworld.article.domain.repository;

import wattt3.realworld.article.domain.FavoriteRelation;

public interface FavoriteRelationRepository {

    boolean existsByArticleIdAndUserId(Long articleId, Long userId);

    void save(FavoriteRelation favoriteRelation);

    FavoriteRelation getByArticleIdAndUserId(Long articleId, Long userId);

    void delete(FavoriteRelation favoriteRelation);
}
