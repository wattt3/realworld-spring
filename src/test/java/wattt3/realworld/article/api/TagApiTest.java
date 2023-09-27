package wattt3.realworld.article.api;

import java.util.List;
import org.junit.jupiter.api.Test;
import wattt3.realworld.common.ApiTest;
import wattt3.realworld.common.Scenario;

public class TagApiTest extends ApiTest {

    @Test
    void getTags() {
        Scenario.userApi().registerUserApi()
                .articleApi().tag(List.of("tag")).createArticle(tokenManager.generate(email))
                .tagApi().getTags();
    }
}
