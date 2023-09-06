package wattt3.realworld.profile.application.service;

import org.springframework.stereotype.Service;
import wattt3.realworld.common.exception.CommonException;
import wattt3.realworld.common.exception.ErrorCode;
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

    public ProfileResponse follow(String followeeName, String followerEmail) {
        User follower = userRepository.findByEmail(followerEmail)
            .orElseThrow(() -> {
                throw new CommonException(ErrorCode.NOT_FOUND_USER,
                    "존재하지 않는 유저입니다. email : %s".formatted(followerEmail));
            });
        User followee = userRepository.findByUsername(followeeName)
            .orElseThrow(() -> {
                throw new CommonException(ErrorCode.NOT_FOUND_USER,
                    "존재하지 않는 유저입니다. username : %s".formatted(followeeName));
            });

        FollowRelation followRelation = new FollowRelation(followee.getId(), follower.getId());

        followRelationRepository.save(followRelation);

        return followee.toProfile(true);
    }

    public ProfileResponse getProfile(String followeeName, String followerEmail) {
        User follower = userRepository.findByEmail(followerEmail)
            .orElseThrow(() -> {
                throw new CommonException(ErrorCode.NOT_FOUND_USER,
                    "존재하지 않는 유저입니다. email : %s".formatted(followerEmail));
            });
        User followee = userRepository.findByUsername(followeeName)
            .orElseThrow(() -> {
                throw new CommonException(ErrorCode.NOT_FOUND_USER,
                    "존재하지 않는 유저입니다. username : %s".formatted(followeeName));
            });

        boolean following = followRelationRepository.findByFolloweeIdAndFollowerId(
            followee.getId(), follower.getId()).isPresent();

        return followee.toProfile(following);
    }
}
