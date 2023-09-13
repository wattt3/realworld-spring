package wattt3.realworld.article.domain;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode(of = {"articleId", "userId"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteRelationId implements Serializable {

    private Long articleId;
    private Long userId;

    public FavoriteRelationId(Long articleId, Long userId) {
        this.articleId = articleId;
        this.userId = userId;
    }
}
