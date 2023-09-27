package wattt3.realworld.article.api;

import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;
import wattt3.realworld.common.Scenario;

public class TagApi {

    public Scenario getTags() {
        RestAssured.given().log().all()
                .when()
                .get("/tags")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}