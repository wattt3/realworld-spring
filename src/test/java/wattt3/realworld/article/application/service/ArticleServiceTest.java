package wattt3.realworld.article.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static wattt3.realworld.article.domain.ArticleFixture.aArticle;
import static wattt3.realworld.user.domain.UserFixture.aUser;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import wattt3.realworld.article.application.request.UpdateArticleRequest;
import wattt3.realworld.article.application.response.SingleArticleResponse;
import wattt3.realworld.article.domain.Article;
import wattt3.realworld.article.domain.repository.ArticleRepository;
import wattt3.realworld.article.domain.repository.FavoriteRelationRepository;
import wattt3.realworld.profile.domain.FollowRelation;
import wattt3.realworld.profile.domain.FollowRelationRepository;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

class ArticleServiceTest {

    @Test
    void get() {
        var sut = new ArticleService(new ArticleRepositoryStub(), new UserRepositoryStub(),
                new FavoriteRelationRepositoryStub(), new FollowRelationRepositoryStub());

        SingleArticleResponse response = sut.getArticle("a-title", 1L);

        assertAll(() -> assertThat(response.article().slug()).isEqualTo("a-title"),
                () -> assertThat(response.article().title()).isEqualTo("a title"),
                () -> assertThat(response.article().description()).isEqualTo("description"),
                () -> assertThat(response.article().body()).isEqualTo("body"));
    }

    @Test
    void getFeedArticles() {
        var sut = new ArticleService(new ArticleRepositoryStub(), new UserRepositoryStub(),
                new FavoriteRelationRepositoryStub(), new FollowRelationRepositoryStub());
        var offset = 0;
        var limit = 20;
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Direction.DESC, "createdAt"));

        var response = sut.getFeedArticles(pageable, 1L);

        assertThat(response.articlesCount()).isEqualTo(2);
    }

    @Test
    void update() {
        var sut = new ArticleService(new ArticleRepositoryStub(), new UserRepositoryStub(),
                new FavoriteRelationRepositoryStub(), new FollowRelationRepositoryStub());
        var request = new UpdateArticleRequest("A TITLE", "DESCRIPTION", "BODY");

        var response = sut.updateArticle(request, "a-title", 1L);

        assertAll(() -> assertThat(response.article().slug()).isEqualTo("A-TITLE"),
                () -> assertThat(response.article().title()).isEqualTo("A TITLE"),
                () -> assertThat(response.article().description()).isEqualTo("DESCRIPTION"),
                () -> assertThat(response.article().body()).isEqualTo("BODY"));
    }

    @Test
    @DisplayName("작성자가 아닌 유저가 수정시 예외 발생")
    void updateByNotAuthor() {
        var sut = new ArticleService(new ArticleRepositoryStub(), new UserRepositoryStub(),
                new FavoriteRelationRepositoryStub(), new FollowRelationRepositoryStub());
        var request = new UpdateArticleRequest("A TITLE", "DESCRIPTION", "BODY");

        assertThatThrownBy(() -> sut.updateArticle(request, "a-title", 2L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public class ArticleRepositoryStub implements ArticleRepository {

        @Override
        public Article save(Article article) {
            return null;
        }

        @Override
        public Article getBySlug(String slug) {
            return aArticle().build();
        }

        @Override
        public void delete(Article article) {

        }

        @Override
        public Page<Article> findByAuthorIds(List<Long> authorIds, Pageable pageable) {
            return new PageImpl<>(List.of(
                    aArticle().build(),
                    aArticle().id(2L)
                            .slug("a-title-2")
                            .title("a title 2")
                            .build()
            ));
        }
    }

    public class UserRepositoryStub implements UserRepository {

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


    private class FavoriteRelationRepositoryStub implements FavoriteRelationRepository {

        @Override
        public boolean existsByArticleIdAndUserId(Long articleId, Long userId) {
            return false;
        }
    }

    private class FollowRelationRepositoryStub implements FollowRelationRepository {

        @Override
        public FollowRelation save(FollowRelation followRelation) {
            return null;
        }

        @Override
        public List<FollowRelation> findAll() {
            return null;
        }

        @Override
        public Optional<FollowRelation> findByFolloweeIdAndFollowerId(Long followeeId,
                Long followerId) {
            return Optional.empty();
        }

        @Override
        public boolean existsByFolloweeIdAndFollowerId(Long followeeId, Long followerId) {
            return false;
        }

        @Override
        public void deleteByFolloweeIdAndFollowerId(Long followeeId, Long followerId) {

        }

        @Override
        public List<FollowRelation> findByFollowerId(Long userId) {
            return List.of(new FollowRelation(2L, 1L));
        }
    }
}