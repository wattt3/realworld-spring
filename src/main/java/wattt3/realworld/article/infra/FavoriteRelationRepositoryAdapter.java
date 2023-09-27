package wattt3.realworld.article.infra;

import org.springframework.stereotype.Repository;
import wattt3.realworld.article.domain.FavoriteRelation;
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

    @Override
    public void save(FavoriteRelation favoriteRelation) {
        jpaFavoriteRelationRepository.save(favoriteRelation);
    }

    @Override
    public FavoriteRelation getByArticleIdAndUserId(Long articleId, Long userId) {
        return jpaFavoriteRelationRepository.findByFavoriteRelationId_ArticleIdAndFavoriteRelationId_UserId(
                        articleId, userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 즐겨찾기 목록입니다."));
    }

    @Override
    public void delete(FavoriteRelation favoriteRelation) {
        jpaFavoriteRelationRepository.delete(favoriteRelation);
    }
}
