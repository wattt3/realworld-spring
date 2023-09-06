package wattt3.realworld.profile.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wattt3.realworld.common.ApiTest;
import wattt3.realworld.common.Scenario;
import wattt3.realworld.profile.domain.FollowRelationRepository;

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

}
