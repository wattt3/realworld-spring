package wattt3.realworld.profile.domain;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode(of = {"followeeId", "followerId"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowRelationId implements Serializable {

    private Long followeeId;

    private Long followerId;

    public FollowRelationId(Long followeeId, Long followerId) {
        this.followeeId = followeeId;
        this.followerId = followerId;
    }
}
