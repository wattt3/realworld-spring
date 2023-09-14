package wattt3.realworld.profile.domain;

import java.util.List;

public interface FollowRelationRepository {

    FollowRelation save(FollowRelation followRelation);

    List<FollowRelation> findAll();

    boolean existsByFolloweeIdAndFollowerId(Long followeeId, Long followerId);

    void deleteByFolloweeIdAndFollowerId(Long followeeId, Long followerId);

    List<FollowRelation> findByFollowerId(Long userId);
}
