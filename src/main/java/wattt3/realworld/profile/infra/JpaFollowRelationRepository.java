package wattt3.realworld.profile.infra;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wattt3.realworld.profile.domain.FollowRelation;
import wattt3.realworld.profile.domain.FollowRelationId;

public interface JpaFollowRelationRepository extends
        JpaRepository<FollowRelation, FollowRelationId> {

    boolean existsByFollowRelationId_FolloweeIdAndFollowRelationId_FollowerId(Long followeeId,
            Long followerId);

    void deleteByFollowRelationId_FolloweeIdAndFollowRelationId_FollowerId(
            Long followeeId, Long followerId);

    Optional<FollowRelation> findByFollowRelationId_FolloweeIdAndFollowRelationId_FollowerId(
            Long followeeId, Long followerId);

}
