package wattt3.realworld.article.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import wattt3.realworld.article.application.request.CreateArticleRequest;
import wattt3.realworld.common.Scenario;

public class ArticleApi {

    private final String title = "title";
    private final String description = "description";
    private final String body = "body";
    private final List<String> tag = List.of("tag");

    public Scenario createArticle(String token) {
        var request = new CreateArticleRequest(title, description, body, tag);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Token " + token)
                .body(request)
                .when()
                .post("/articles")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        return new Scenario();
    }
}