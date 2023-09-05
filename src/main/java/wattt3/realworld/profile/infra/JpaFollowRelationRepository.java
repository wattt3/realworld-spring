package wattt3.realworld.profile.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import wattt3.realworld.profile.domain.FollowRelation;

public interface JpaFollowRelationRepository extends JpaRepository<FollowRelation, Long> {

}
