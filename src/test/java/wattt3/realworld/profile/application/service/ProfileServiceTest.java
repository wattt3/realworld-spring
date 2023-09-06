package wattt3.realworld.profile.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static wattt3.realworld.profile.fixture.ProfileFixture.aProfile;
import static wattt3.realworld.user.fixture.UserFixture.aUser;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import wattt3.realworld.profile.application.response.ProfileResponse;
import wattt3.realworld.profile.domain.FollowRelation;
import wattt3.realworld.profile.domain.FollowRelationRepository;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

class ProfileServiceTest {

    @Test
    void getProfile() {
        var profileService = new ProfileService(new StubFollowRelationRepository(),
            new StubUserRepository());

        var response = profileService.getProfile("followee", "user@domain.com");

        assertThat(response)
            .usingRecursiveAssertion()
            .isEqualTo(new ProfileResponse("followee", null, null, true));
    }

    class StubFollowRelationRepository implements FollowRelationRepository {

        @Override
        public FollowRelation save(FollowRelation followRelation) {
            return null;
        }

        @Override
        public List<FollowRelation> findAll() {
            return null;
        }

        @Override
        public Optional<FollowRelation> findByFolloweeIdAndFollowerId(Long followeeId,
            Long followerId) {
            return Optional.of(aProfile().build());
        }
    }

    class StubUserRepository implements UserRepository {

        @Override
        public User save(User user) {
            return null;
        }

        @Override
        public Optional<User> findByEmail(String email) {
            return Optional.of(aUser().build());
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
            return Optional.of(aUser().username("followee").build());
        }
    }

}
