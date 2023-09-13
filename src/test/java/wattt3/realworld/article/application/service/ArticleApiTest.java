package wattt3.realworld.article.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    void createArticle() {
        Scenario.userApi().registerUserApi()
                .articleApi().createArticle(tokenManager.generate(email));

        assertThat(articleRepository.findAll()).hasSize(1);
        assertThat(tagRepository.findAll()).hasSize(1);
    }

}
