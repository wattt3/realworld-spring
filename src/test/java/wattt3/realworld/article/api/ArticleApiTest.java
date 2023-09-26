package wattt3.realworld.article.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wattt3.realworld.article.domain.condition.ArticleSearchCondition;
import wattt3.realworld.article.domain.repository.ArticleRepository;
import wattt3.realworld.article.domain.repository.TagRepository;
import wattt3.realworld.common.ApiTest;
import wattt3.realworld.common.Scenario;

public class ArticleApiTest extends ApiTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TagRepository tagRepository;

    @Test
    void getArticle() {
        Scenario.userApi().registerUserApi()
                .articleApi().createArticle(tokenManager.generate(email))
                .articleApi().getArticle();
    }

    @Test
    void getArticles() {
        Scenario.userApi().registerUserApi()
                .userApi()
                .email("followee@domain.com")
                .username("followee").registerUserApi()
                .userApi()
                .email("followee2@domain.com")
                .username("followee2").registerUserApi()
                .profileApi().follow(tokenManager.generate(email))
                .profileApi().followee("followee2").follow(tokenManager.generate(email))
                .articleApi().createArticle(tokenManager.generate("followee@domain.com"))
                .articleApi()
                .slug("a-title-2")
                .title("a title 2").createArticle(tokenManager.generate("followee@domain.com"))
                .articleApi()
                .slug("a-title-3")
                .title("a title 3").createArticle(tokenManager.generate("followee2@domain.com"))
                .articleApi().getArticles(new ArticleSearchCondition(null, "followee", null), 2);
    }

    // N + 1 Test
    @Test
    void getFeedArticles() {
        Scenario.userApi().registerUserApi()
                .userApi()
                .email("followee@domain.com")
                .username("followee").registerUserApi()
                .userApi()
                .email("followee2@domain.com")
                .username("followee2").registerUserApi()
                .profileApi().follow(tokenManager.generate(email))
                .profileApi().followee("followee2").follow(tokenManager.generate(email))
                .articleApi().createArticle(tokenManager.generate("followee@domain.com"))
                .articleApi()
                .slug("a-title-2")
                .title("a title 2").createArticle(tokenManager.generate("followee@domain.com"))
                .articleApi()
                .slug("a-title-3")
                .title("a title 3").createArticle(tokenManager.generate("followee2@domain.com"))
                .articleApi().getFeedArticles(tokenManager.generate(email), 3);
    }

    @Test
    void createArticle() {
        Scenario.userApi().registerUserApi()
                .articleApi().createArticle(tokenManager.generate(email));

        assertThat(articleRepository.getBySlug("a-title")).isNotNull();
        assertThat(tagRepository.findAll()).hasSize(1);
    }

    @Test
    void updateArticle() {
        Scenario.userApi().registerUserApi()
                .articleApi().createArticle(tokenManager.generate(email))
                .articleApi()
                .slug("a-title")
                .title("A TITLE")
                .updateArticle(tokenManager.generate(email));

        assertThat(articleRepository.getBySlug("A-TITLE")).isNotNull();
    }

    @Test
    void deleteArticle() {
        Scenario.userApi().registerUserApi()
                .articleApi().createArticle(tokenManager.generate(email))
                .articleApi().deleteArticle(tokenManager.generate(email));

        assertThatThrownBy(() -> articleRepository.getBySlug("a-title"))
                .hasMessage("slug: a-title 가 존재하지 않습니다.");
        assertThat(tagRepository.findAll()).hasSize(0);
    }

}
