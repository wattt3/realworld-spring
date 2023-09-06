package wattt3.realworld.profile.infra;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wattt3.realworld.profile.domain.FollowRelation;

public interface JpaFollowRelationRepository extends JpaRepository<FollowRelation, Long> {

    Optional<FollowRelation> findByFollowRelationId_FolloweeIdAndFollowRelationId_FollowerId(
        Long followeeId, Long followerId);

}
