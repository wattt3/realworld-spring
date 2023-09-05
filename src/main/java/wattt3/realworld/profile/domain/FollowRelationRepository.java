package wattt3.realworld.profile.domain;

import java.util.List;

public interface FollowRelationRepository {

    FollowRelation save(FollowRelation followRelation);

    List<FollowRelation> findAll();
}
