package wattt3.realworld.article.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import wattt3.realworld.article.domain.FavoriteRelation;
import wattt3.realworld.article.domain.FavoriteRelationId;

public interface JpaFavoriteRelationRepository extends
        JpaRepository<FavoriteRelation, FavoriteRelationId> {

    boolean existsByFavoriteRelationId_ArticleIdAndFavoriteRelationId_UserId(Long articleId,
            Long userId);
}