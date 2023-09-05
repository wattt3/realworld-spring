package wattt3.realworld.profile.application;

import wattt3.realworld.common.exception.CommonException;
import wattt3.realworld.common.exception.ErrorCode;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

public class ProfileService {

    private final ProfileServiceTest profileServiceTest;
    private final FollowRelationRepository followRelationRepository;
    private final UserRepository userRepository;

    ProfileService(ProfileServiceTest profileServiceTest,
        FollowRelationRepository followRelationRepository,
        UserRepository userRepository) {
        this.profileServiceTest = profileServiceTest;
        this.followRelationRepository = followRelationRepository;
        this.userRepository = userRepository;
    }

    public void follow(String followeeName, String followerEmail) {
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
    }
}
