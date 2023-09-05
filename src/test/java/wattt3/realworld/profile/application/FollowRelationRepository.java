package wattt3.realworld.profile.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

public class FollowRelationRepository {

    private final Map<Pair<Long, Long>, FollowRelation> followRelations = new HashMap<>();

    public void save(FollowRelation followRelation) {
        followRelations.put(
            Pair.of(followRelation.getFolloweeId(), followRelation.getFollowerId()),
            followRelation);
    }

    public List<FollowRelation> findAll() {
        return new ArrayList<>(followRelations.values());
    }
}
