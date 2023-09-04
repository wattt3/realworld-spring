package wattt3.realworld.user.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import wattt3.realworld.common.Scenario;
import wattt3.realworld.user.application.request.LoginUserRequest;
import wattt3.realworld.user.application.request.RegisterUserRequest;

public class UserApi {

    private String email = "name@domain.com";
    private String username = "username";
    private String password = "password";

    public UserApi email(final String email) {
        this.email = email;
        return this;
    }

    public UserApi username(final String username) {
        this.username = username;
        return this;
    }

    public UserApi password(final String password) {
        this.password = password;
        return this;
    }

    public Scenario registerUserApi() {
        RegisterUserRequest request = new RegisterUserRequest(email, username, password);

        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/users")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());

        return new Scenario();
    }

    public Scenario registerDuplicateUserApi() {
        RegisterUserRequest request = new RegisterUserRequest(email, username, password);

        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/users")
            .then().log().all()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());

        return new Scenario();
    }

    public Scenario loginApi() {
        LoginUserRequest request = new LoginUserRequest(email, password);

        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/users/login")
            .then().log().all()
            .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }

    public Scenario getCurrentUserApi(String token) {
        RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Token " + token)
            .contentType(ContentType.JSON)
            .when()
            .get("/user")
            .then().log().all()
            .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
