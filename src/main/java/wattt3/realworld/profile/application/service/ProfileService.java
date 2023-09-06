package wattt3.realworld.profile.application.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
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

    @Transactional(readOnly = true)
    public ProfileResponse getProfile(String followeeName, String followerEmail) {
        Optional<User> optionalFollower = userRepository.findByEmail(followerEmail);
        User followee = userRepository.findByUsername(followeeName)
            .orElseThrow(() -> {
                throw new CommonException(ErrorCode.NOT_FOUND_USER,
                    "존재하지 않는 유저입니다. username : %s".formatted(followeeName));
            });

        if (optionalFollower.isPresent()) {
            boolean following = followRelationRepository.findByFolloweeIdAndFollowerId(
                followee.getId(), optionalFollower.get().getId()).isPresent();
            return followee.toProfile(following);
        }

        return followee.toProfile(false);
    }

    @Transactional
    public ProfileResponse unfollow(String followeeName, String followerEmail) {
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

        followRelationRepository.deleteByFolloweeIdAndFollowerId(followee.getId(),
            follower.getId());

        return followee.toProfile(false);
    }
}
