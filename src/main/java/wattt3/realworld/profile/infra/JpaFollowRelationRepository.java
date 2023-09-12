package wattt3.realworld.profile.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import wattt3.realworld.profile.domain.FollowRelation;

import java.util.Optional;

public interface JpaFollowRelationRepository extends JpaRepository<FollowRelation, Long> {
    boolean existsByFollowRelationId_FolloweeIdAndFollowRelationId_FollowerId(Long followeeId, Long followerId);

    void deleteByFollowRelationId_FolloweeIdAndFollowRelationId_FollowerId(
            Long followeeId, Long followerId);

    Optional<FollowRelation> findByFollowRelationId_FolloweeIdAndFollowRelationId_FollowerId(
            Long followeeId, Long followerId);

}
