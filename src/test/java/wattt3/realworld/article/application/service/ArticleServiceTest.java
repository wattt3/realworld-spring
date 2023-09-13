package wattt3.realworld.article.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static wattt3.realworld.user.domain.UserFixture.aUser;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wattt3.realworld.article.application.request.CreateArticleRequest;
import wattt3.realworld.article.domain.repository.ArticleRepository;
import wattt3.realworld.article.domain.repository.TagRepository;
import wattt3.realworld.user.application.request.RegisterUserRequest;
import wattt3.realworld.user.application.service.UserService;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

@SpringBootTest
@Transactional
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserService userService;

    @Test
    void createArticle() {
        CreateArticleRequest request = new CreateArticleRequest("title", "description", "body",
                List.of("tag"));

        userService.register(new RegisterUserRequest("name@domain.com", "username", "password"));
        articleService.createArticle(request, 1L);

        assertThat(articleRepository.findAll()).hasSize(1);
        assertThat(tagRepository.findAll()).hasSize(1);
    }

    class StubUserRepository implements UserRepository {

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
