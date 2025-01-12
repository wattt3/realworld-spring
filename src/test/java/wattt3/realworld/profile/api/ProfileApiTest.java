package wattt3.realworld.profile.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wattt3.realworld.common.ApiTest;
import wattt3.realworld.common.Scenario;
import wattt3.realworld.profile.domain.FollowRelationRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileApiTest extends ApiTest {

    @Autowired
    private FollowRelationRepository followRelationRepository;

    @Test
    @DisplayName("유저 팔로우")
    void follow() {
        Scenario.userApi().registerUserApi()
                .userApi().email("followee@domain.com").username("followee").registerUserApi();

        Scenario.profileApi().follow(tokenManager.generate(email));

        assertThat(followRelationRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("유저 프로필 불러오기")
    void getProfile() {
        Scenario.userApi().email("followee@domain.com").username("followee").registerUserApi();

        Scenario.profileApi().getProfileApi();
    }

    @Test
    @DisplayName("유저 언팔로우")
    void unfollow() {
        Scenario.userApi().registerUserApi()
                .userApi().email("followee@domain.com").username("followee").registerUserApi();

        Scenario.profileApi().unfollow(tokenManager.generate(email));

        assertThat(followRelationRepository.findAll()).hasSize(0);
    }

}
