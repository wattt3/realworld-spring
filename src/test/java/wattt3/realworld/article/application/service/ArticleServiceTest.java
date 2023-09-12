package wattt3.realworld.article.application.service;

import org.junit.jupiter.api.Test;

import java.util.List;

public class ArticleServiceTest {

    @Test
    void createArticle() {
        new CreateArticleRequest("title", "description", "body", List.of(new Tag("tag")));
    }

    public record CreateArticleRequest(String title, String description, String body, List<Tag> tag) {
    }

    public class Tag {

        private final String name;

        public Tag(String name) {
            this.name = name;
        }
    }
}
