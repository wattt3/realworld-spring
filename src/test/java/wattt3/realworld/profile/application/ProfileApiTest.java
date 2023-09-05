package wattt3.realworld.profile.application;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

        RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Token " + tokenManager.generate(email))
            .when()
            .post("/profiles/followee/follow")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());

        assertThat(followRelationRepository.findAll()).hasSize(1);
    }
}
