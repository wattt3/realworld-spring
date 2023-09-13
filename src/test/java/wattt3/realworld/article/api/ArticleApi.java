package wattt3.realworld.article.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import wattt3.realworld.article.application.request.CreateArticleRequest;
import wattt3.realworld.article.application.request.UpdateArticleRequest;
import wattt3.realworld.common.Scenario;

public class ArticleApi {

    private String slug = "a-title";
    private String title = "a title";
    private String description = "description";
    private String body = "body";
    private List<String> tag = List.of("tag");

    public ArticleApi slug(String slug) {
        this.slug = slug;
        return this;
    }

    public ArticleApi title(String title) {
        this.title = title;
        return this;
    }

    public ArticleApi description(String description) {
        this.description = description;
        return this;
    }

    public ArticleApi body(String body) {
        this.body = body;
        return this;
    }

    public ArticleApi tag(List<String> tag) {
        this.tag = tag;
        return this;
    }

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

    public Scenario updateArticle(String token) {
        var request = new UpdateArticleRequest(title, description, body);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Token " + token)
                .body(request)
                .when()
                .put("/articles/" + slug)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }

    public Scenario deleteArticle(String token) {
        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Token " + token)
                .when()
                .delete("/articles/" + slug)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}