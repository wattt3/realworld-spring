package wattt3.realworld.profile.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static wattt3.realworld.user.domain.UserFixture.aUser;

import java.util.List;
import org.junit.jupiter.api.Test;
import wattt3.realworld.profile.application.response.ProfileResponse;
import wattt3.realworld.profile.domain.FollowRelation;
import wattt3.realworld.profile.domain.FollowRelationRepository;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

class ProfileServiceTest {

    private final ProfileService profileService = new ProfileService(
            new StubFollowRelationRepository(),
            new StubUserRepository());

    @Test
    void getProfile() {
        var response = profileService.getProfile("followee", 2L);

        assertThat(response)
                .usingRecursiveAssertion()
                .isEqualTo(new ProfileResponse("followee", null, null, true));
    }

    @Test
    void deleteFollow() {
        var response = profileService.unfollow("followee", 2L);

        assertThat(response)
                .usingRecursiveAssertion()
                .isEqualTo(new ProfileResponse("followee", null, null, false));
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
        public boolean existsByFolloweeIdAndFollowerId(Long followeeId, Long followerId) {
            return true;
        }

        @Override
        public void deleteByFolloweeIdAndFollowerId(Long followeeId, Long followerId) {
        }

        @Override
        public List<FollowRelation> findByFollowerId(Long userId) {
            return null;
        }
    }

    class StubUserRepository implements UserRepository {

        @Override
        public User save(User user) {
            return null;
        }

        @Override
        public User getById(Long userId) {
            return null;
        }

        @Override
        public User getByEmail(String email) {
            return null;
        }

        @Override
        public boolean existsByEmailOrUsername(String email, String username) {
            return false;
        }

        @Override
        public List<User> findAll() {
            return null;
        }

        @Override
        public User getByUsername(String username) {
            return aUser().username("followee").build();
        }
    }

}
