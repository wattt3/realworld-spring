package wattt3.realworld.article.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static wattt3.realworld.user.domain.UserFixture.aUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public record CreateArticleRequest(String title,
                                       String description,
                                       String body,
                                       List<String> tagList) {

    }

    public record Author(String username, String bio, String image, boolean following) {

        public static Author of(User user, boolean following) {
            return new Author(user.getUsername(), user.getBio(), user.getImage(), following);
        }
    }

    public record ArticleDTO(String slug,
                             String title,
                             String description,
                             String body,
                             List<Tag> tagList,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt,
                             boolean favorited,
                             int favoritesCount,
                             Author author) {

        public static ArticleDTO of(Article article, boolean favorited, Author author) {
            return new ArticleDTO(article.slug, article.title, article.description, article.body,
                    article.tagList, article.createdAt, article.updatedAt, favorited,
                    article.favorited.size(), author);
        }

    }

    public class Tag {

        private final String name;
        private Long id;

        public Tag(String name) {
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    public class ArticleService {

        private final ArticleRepository articleRepository;
        private final TagRepository tagRepository;
        private final UserRepository userRepository;

        public ArticleService(ArticleRepository articleRepository, TagRepository tagRepository,
                UserRepository userRepository) {
            this.articleRepository = articleRepository;
            this.tagRepository = tagRepository;
            this.userRepository = userRepository;
        }

        public void createArticle(CreateArticleRequest request, Long userId) {
            User user = userRepository.getById(userId);

            List<Tag> tags = request.tagList().stream()
                    .map(name -> tagRepository.existsByName(name) ? tagRepository.getByName(name)
                            : tagRepository.save(new Tag(name)))
                    .toList();

            Article article = new Article(request.title(), request.title(), request.description(),
                    request.body(), tags, LocalDateTime.now(), LocalDateTime.now(),
                    Collections.emptyList(), userId);

            articleRepository.save(article);

            ArticleDTO.of(article, false, Author.of(user, false));
        }
    }

    public class ArticleRepository {

        private final Map<Long, Article> articles = new HashMap<>();
        private Long sequence = 1L;

        public void save(Article article) {
            article.setId(sequence);
            ++sequence;
            articles.put(article.getId(), article);
        }

        public List<Article> findAll() {
            return new ArrayList<>(articles.values());
        }
    }

    public class Article {

        private final String slug;
        private final String title;
        private final String description;
        private final String body;
        private final List<Tag> tagList;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        private final List<Favorite> favorited;
        private final Long authorId;
        private Long id;

        public Article(
                String slug,
                String title,
                String description,
                String body,
                List<Tag> tagList,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                List<Favorite> favorited,
                Long authorId) {
            this.slug = slug;
            this.title = title;
            this.description = description;
            this.body = body;
            this.tagList = tagList;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.favorited = favorited;
            this.authorId = authorId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
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

    public class Favorite {

        private Long articleId;
        private Long userId;

    }

    private class TagRepository {

        private final Map<Long, Tag> tags = new HashMap<>();
        private Long sequence = 1L;


        public List<Tag> findAll() {
            return new ArrayList<>(tags.values());
        }

        public boolean existsByName(String name) {
            return tags.values().stream()
                    .anyMatch(tag -> tag.name.equals(name));
        }

        public Tag getByName(String name) {
            return tags.values().stream()
                    .filter(tag -> tag.name.equals(name))
                    .findAny()
                    .orElse(null);
        }

        public Tag save(Tag tag) {
            tag.setId(sequence);
            ++sequence;
            tags.put(tag.getId(), tag);
            return tag;
        }
    }
}
