package wattt3.realworld.profile.api;

import io.restassured.RestAssured;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import wattt3.realworld.common.Scenario;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class ProfileApi {

    public Scenario getProfileApi() {
        RestAssured.given().log().all()
                .when()
                .get("/profiles/followee")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("profile.username", equalTo("followee"))
                .body("profile.bio", equalTo(null))
                .body("profile.image", equalTo(null))
                .body("profile.following", equalTo(false));

        return new Scenario();
    }

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

    public Scenario unfollow(String token) {
        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Token " + token)
                .when()
                .delete("/profiles/followee/follow")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("profile", hasKey("username"))
                .body("profile", hasKey("bio"))
                .body("profile", hasKey("image"))
                .body("profile", hasKey("following"));

        return new Scenario();
    }
}
