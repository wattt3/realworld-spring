package wattt3.realworld.article.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Table(name = "favorite_relation")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteRelation {

    @EmbeddedId
    private FavoriteRelationId favoriteRelationId;

    public FavoriteRelation(Long articleId, Long userId) {
        this.favoriteRelationId = new FavoriteRelationId(articleId, userId);
    }
}
