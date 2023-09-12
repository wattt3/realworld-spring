package wattt3.realworld.profile.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follow_relation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowRelation {

    @EmbeddedId
    private FollowRelationId followRelationId;

    public FollowRelation(Long followeeId, Long followerId) {
        this(new FollowRelationId(followeeId, followerId));
    }

    private FollowRelation(FollowRelationId followRelationId) {
        this.followRelationId = followRelationId;
    }

    public Long getFolloweeId() {
        return followRelationId.getFolloweeId();
    }

    public Long getFollowerId() {
        return followRelationId.getFollowerId();
    }
}
