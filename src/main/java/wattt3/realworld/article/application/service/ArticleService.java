package wattt3.realworld.article.application.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import wattt3.realworld.article.application.dto.ArticleDTO;
import wattt3.realworld.article.application.request.CreateArticleRequest;
import wattt3.realworld.article.domain.Article;
import wattt3.realworld.article.domain.ArticleRepository;
import wattt3.realworld.article.domain.Author;
import wattt3.realworld.article.domain.Tag;
import wattt3.realworld.article.domain.TagRepository;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

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
