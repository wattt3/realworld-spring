package wattt3.realworld.profile.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wattt3.realworld.profile.application.response.ProfileResponse;
import wattt3.realworld.profile.domain.FollowRelation;
import wattt3.realworld.profile.domain.FollowRelationRepository;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

@Service
public class ProfileService {

    private final FollowRelationRepository followRelationRepository;
    private final UserRepository userRepository;

    public ProfileService(FollowRelationRepository followRelationRepository,
                          UserRepository userRepository) {
        this.followRelationRepository = followRelationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProfileResponse follow(String followeeName, Long followerId) {
        User followee = userRepository.getByUsername(followeeName);

        FollowRelation followRelation = new FollowRelation(followee.getId(), followerId);
        followRelationRepository.save(followRelation);

        return followee.toProfile(true);
    }

    @Transactional(readOnly = true)
    public ProfileResponse getProfile(String followeeName, Long followerId) {
        User followee = userRepository.getByUsername(followeeName);

        if (followerId != null) {
            boolean following = followRelationRepository.existsByFolloweeIdAndFollowerId(
                    followee.getId(), followerId);
            return followee.toProfile(following);
        }

        return followee.toProfile(false);
    }

    @Transactional
    public ProfileResponse unfollow(String followeeName, Long followerId) {
        User followee = userRepository.getByUsername(followeeName);

        followRelationRepository.deleteByFolloweeIdAndFollowerId(followee.getId(), followerId);

        return followee.toProfile(false);
    }
}
