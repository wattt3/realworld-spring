package wattt3.realworld.profile.api;

import static org.hamcrest.Matchers.hasKey;

import io.restassured.RestAssured;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import wattt3.realworld.common.Scenario;

public class ProfileApi {

    public Scenario follow(String token) {
        RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Token " + token)
            .when()
            .post("/profiles/followee/follow")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value())
            .body("profile", hasKey("username"))
            .body("profile", hasKey("bio"))
            .body("profile", hasKey("image"))
            .body("profile", hasKey("following"));

        return new Scenario();
    }
}
