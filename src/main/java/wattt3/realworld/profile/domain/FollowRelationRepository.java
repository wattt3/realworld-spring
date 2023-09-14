package wattt3.realworld.profile.domain;

import java.util.List;
import java.util.Optional;

public interface FollowRelationRepository {

    FollowRelation save(FollowRelation followRelation);

    List<FollowRelation> findAll();

    Optional<FollowRelation> findByFolloweeIdAndFollowerId(Long followeeId, Long followerId);

    boolean existsByFolloweeIdAndFollowerId(Long followeeId, Long followerId);

    void deleteByFolloweeIdAndFollowerId(Long followeeId, Long followerId);

    List<FollowRelation> findByFollowerId(Long userId);
}
