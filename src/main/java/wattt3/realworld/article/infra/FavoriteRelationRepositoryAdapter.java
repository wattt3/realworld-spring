package wattt3.realworld.article.infra;

import org.springframework.stereotype.Repository;
import wattt3.realworld.article.domain.repository.FavoriteRelationRepository;

@Repository
public class FavoriteRelationRepositoryAdapter implements FavoriteRelationRepository {

    private final JpaFavoriteRelationRepository jpaFavoriteRelationRepository;

    public FavoriteRelationRepositoryAdapter(
            JpaFavoriteRelationRepository jpaFavoriteRelationRepository) {
        this.jpaFavoriteRelationRepository = jpaFavoriteRelationRepository;
    }

    @Override
    public boolean existsByArticleIdAndUserId(Long articleId, Long userId) {
        return jpaFavoriteRelationRepository.existsByFavoriteRelationId_ArticleIdAndFavoriteRelationId_UserId(
                articleId, userId);
    }
}
