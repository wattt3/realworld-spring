package wattt3.realworld.profile.application;

import static org.assertj.core.api.Assertions.assertThat;
import static wattt3.realworld.user.fixture.UserFixture.aUser;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wattt3.realworld.profile.application.service.ProfileService;
import wattt3.realworld.profile.domain.FollowRelationRepository;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

public class ProfileServiceTest {

    private ProfileService profileService;
    private FollowRelationRepository followRelationRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new StubUserRepository();
        followRelationRepository = new FollowRelationRepository();
        profileService = new ProfileService(followRelationRepository, userRepository);
    }

    @Test
    void follow() {
        profileService.follow("followeeName", "followerEmail");

        assertThat(followRelationRepository.findAll()).hasSize(1);
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
