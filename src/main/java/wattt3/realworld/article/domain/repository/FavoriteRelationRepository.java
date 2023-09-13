package wattt3.realworld.article.domain.repository;

public interface FavoriteRelationRepository {

    boolean existsByArticleIdAndUserId(Long articleId, Long userId);

}
