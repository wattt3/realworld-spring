package wattt3.realworld.article.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static wattt3.realworld.user.domain.UserFixture.aUser;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

public class ArticleServiceTest {

    private ArticleService articleService;
    private ArticleRepository articleRepository;
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        articleRepository = new ArticleRepository();
        UserRepository userRepository = new StubUserRepository();
        tagRepository = new TagRepository();
        articleService = new ArticleService(articleRepository, tagRepository, userRepository);
    }

    @Test
    void createArticle() {
        CreateArticleRequest request = new CreateArticleRequest("title", "description", "body",
                List.of("tag"));

        articleService.createArticle(request, 1L);

        assertThat(articleRepository.findAll()).hasSize(1);
        assertThat(tagRepository.findAll()).hasSize(1);
    }

    private class StubUserRepository implements UserRepository {

        @Override
        public User save(User user) {
            return null;
        }

        @Override
        public User getById(Long userId) {
            return aUser().build();
        }

        @Override
        public User getByEmail(String email) {
            return null;
        }

        @Override
        public User getByUsername(String username) {
            return null;
        }

        @Override
        public boolean existsByEmailOrUsername(String email, String username) {
            return false;
        }

        @Override
        public List<User> findAll() {
            return null;
        }
    }

}
