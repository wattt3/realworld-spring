package wattt3.realworld.article.application.service;

import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wattt3.realworld.article.application.dto.ArticleDTO;
import wattt3.realworld.article.application.dto.AuthorDTO;
import wattt3.realworld.article.application.request.CreateArticleRequest;
import wattt3.realworld.article.application.request.UpdateArticleRequest;
import wattt3.realworld.article.application.response.MultipleArticleResponse;
import wattt3.realworld.article.application.response.SingleArticleResponse;
import wattt3.realworld.article.domain.Article;
import wattt3.realworld.article.domain.Tag;
import wattt3.realworld.article.domain.repository.ArticleRepository;
import wattt3.realworld.article.domain.repository.FavoriteRelationRepository;
import wattt3.realworld.profile.domain.FollowRelation;
import wattt3.realworld.profile.domain.FollowRelationRepository;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final FavoriteRelationRepository favoriteRelationRepository;
    private final FollowRelationRepository followRelationRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository,
            FavoriteRelationRepository favoriteRelationRepository,
            FollowRelationRepository followRelationRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.favoriteRelationRepository = favoriteRelationRepository;
        this.followRelationRepository = followRelationRepository;
    }

    @Transactional(readOnly = true)
    public SingleArticleResponse getArticle(String slug, Long userId) {
        Article article = articleRepository.getBySlug(slug);

        return new SingleArticleResponse(ArticleDTO.of(article, isFavorite(userId, article),
                AuthorDTO.of(article.getAuthor(), isFollowing(userId, article))));
    }

    @Transactional(readOnly = true)
    public MultipleArticleResponse getFeedArticles(Pageable pageable, Long userId) {
        List<Long> followees = followRelationRepository.findByFollowerId(userId).stream()
                .map(FollowRelation::getFolloweeId)
                .toList();

        Page<Article> articlePages = articleRepository.findByAuthorIds(followees, pageable);

        List<ArticleDTO> articles = articlePages.stream()
                .map(article -> ArticleDTO.of(article, isFavorite(userId, article),
                        AuthorDTO.of(article.getAuthor(), isFollowing(userId, article))))
                .toList();

        return new MultipleArticleResponse(articles, articles.size());
    }

    @Transactional
    public SingleArticleResponse createArticle(CreateArticleRequest request, Long userId) {
        User user = userRepository.getById(userId);

        Article article = new Article(request.title(), request.description(),
                request.body(), Collections.emptyList(), Collections.emptyList(), user);

        List<Tag> tags = request.tagList().stream()
                .map(name -> new Tag(name, article))
                .toList();
        article.addTags(tags);

        articleRepository.save(article);

        return new SingleArticleResponse(ArticleDTO.of(article, false, AuthorDTO.of(user, false)));
    }

    @Transactional
    public SingleArticleResponse updateArticle(UpdateArticleRequest request, String slug,
            Long userId) {
        Article article = articleRepository.getBySlug(slug);

        article.update(request.title(), request.description(), request.body(), userId);

        return new SingleArticleResponse(ArticleDTO.of(article, isFavorite(userId, article),
                AuthorDTO.of(article.getAuthor(), isFollowing(userId, article))));
    }

    @Transactional
    public void deleteArticle(String slug, Long userId) {
        Article article = articleRepository.getBySlug(slug);

        article.delete(userId);

        articleRepository.delete(article);
    }

    private boolean isFavorite(Long userId, Article article) {
        return favoriteRelationRepository.existsByArticleIdAndUserId(article.getId(), userId);
    }

    private boolean isFollowing(Long userId, Article article) {
        return followRelationRepository.existsByFolloweeIdAndFollowerId(
                article.getAuthor().getId(), userId);
    }
}
