package wattt3.realworld.profile.infra;

import java.util.List;
import org.springframework.stereotype.Repository;
import wattt3.realworld.profile.domain.FollowRelation;
import wattt3.realworld.profile.domain.FollowRelationRepository;

@Repository
public class FollowRelationAdapter implements FollowRelationRepository {

    private final JpaFollowRelationRepository jpaFollowRelationRepository;

    public FollowRelationAdapter(JpaFollowRelationRepository jpaFollowRelationRepository) {
        this.jpaFollowRelationRepository = jpaFollowRelationRepository;
    }

    @Override
    public FollowRelation save(FollowRelation followRelation) {
        return jpaFollowRelationRepository.save(followRelation);
    }

    @Override
    public List<FollowRelation> findAll() {
        return jpaFollowRelationRepository.findAll();
    }
}
