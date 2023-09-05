package wattt3.realworld.profile.application;

import static org.assertj.core.api.Assertions.assertThat;
import static wattt3.realworld.user.fixture.UserFixture.aUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wattt3.realworld.common.exception.CommonException;
import wattt3.realworld.common.exception.ErrorCode;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

public class ProfileServiceTest {

    private ProfileService profileService;
    private FollowRelationRepository followRelationRepository;
    private UserRepository stubUserRepository;

    @BeforeEach
    void setUp() {
        stubUserRepository = new StubUserRepository();
        followRelationRepository = new FollowRelationRepository();
        profileService = new ProfileService(followRelationRepository, stubUserRepository);
    }

    @Test
    void follow() {
        profileService.follow("followeeName", "followerEmail");

        assertThat(followRelationRepository.findAll()).hasSize(1);
    }

    public class ProfileService {

        private final FollowRelationRepository followRelationRepository;
        private final UserRepository userRepository;

        private ProfileService(FollowRelationRepository followRelationRepository,
            UserRepository userRepository) {
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

    public class FollowRelation {

        private final Long followeeId;

        private final Long followerId;

        public FollowRelation(Long followeeId, Long followerId) {
            this.followeeId = followeeId;
            this.followerId = followerId;
        }

        public Long getFolloweeId() {
            return followeeId;
        }

        public Long getFollowerId() {
            return followerId;
        }
    }

    private class StubUserRepository implements UserRepository {

        @Override
        public User save(User user) {
            return null;
        }

        @Override
        public Optional<User> findByEmail(String email) {
            User user = aUser().build();
            return Optional.of(user);
        }

        @Override
        public Optional<User> findByEmailOrUsername(String email, String username) {
            return Optional.empty();
        }

        @Override
        public List<User> findAll() {
            return null;
        }

        @Override
        public Optional<User> findByUsername(String username) {
            return Optional.of(aUser().email("followee@domain.com")
                .username("followee")
                .build());
        }
    }
}
