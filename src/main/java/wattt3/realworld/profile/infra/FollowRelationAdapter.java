package wattt3.realworld.profile.infra;

import java.util.List;
import java.util.Optional;
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

    @Override
    public Optional<FollowRelation> findByFolloweeIdAndFollowerId(Long followeeId,
        Long followerId) {
        return jpaFollowRelationRepository.findByFollowRelationId_FolloweeIdAndFollowRelationId_FollowerId(
            followeeId, followerId);
    }

    @Override
    public void deleteByFolloweeIdAndFollowerId(Long followeeId, Long followerId) {
        jpaFollowRelationRepository.deleteByFollowRelationId_FolloweeIdAndFollowRelationId_FollowerId(
            followeeId, followerId);
    }
}
