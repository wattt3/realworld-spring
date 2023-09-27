package wattt3.realworld.article.api;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import wattt3.realworld.article.application.request.AddCommentRequest;
import wattt3.realworld.article.application.request.CreateArticleRequest;
import wattt3.realworld.article.application.request.UpdateArticleRequest;
import wattt3.realworld.article.domain.condition.ArticleSearchCondition;
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

    public Scenario getArticle() {
        RestAssured.given().log().all()
                .when()
                .get("/articles/" + slug)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }

    public Scenario getArticles(ArticleSearchCondition condition, int expectedSize) {
        RestAssured.given().log().all()
                .when()
                .param("tag", condition.tag())
                .param("author", condition.author())
                .param("favorited", condition.favorited())
                .get("/articles")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("articlesCount", equalTo(expectedSize));

        return new Scenario();
    }

    public Scenario getFeedArticles(String token, int expectedSize) {
        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Token " + token)
                .when()
                .get("/articles/feed")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("articlesCount", equalTo(expectedSize));

        return new Scenario();
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

    public Scenario getComments() {
        RestAssured.given().log().all()
                .when()
                .get("/articles/" + slug + "/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }

    public Scenario addComment(String token) {
        var request = new AddCommentRequest(body);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Token " + token)
                .body(request)
                .when()
                .post("/articles/" + slug + "/comments")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        return new Scenario();
    }

    public Scenario deleteComment(Long commentId, String token) {
        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Token " + token)
                .when()
                .delete("/articles/" + slug + "/comments/" + commentId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }

    public Scenario favoriteArticle(String token) {
        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Token " + token)
                .when()
                .post("/articles/" + slug + "/favorite")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        return new Scenario();
    }

    public Scenario unFavoriteArticle(String token) {
        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Token " + token)
                .when()
                .delete("/articles/" + slug + "/favorite")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}