package wattt3.realworld.article.application.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import wattt3.realworld.article.application.dto.ArticleDTO;
import wattt3.realworld.article.application.dto.AuthorDTO;
import wattt3.realworld.article.application.request.CreateArticleRequest;
import wattt3.realworld.article.domain.Article;
import wattt3.realworld.article.domain.Tag;
import wattt3.realworld.article.domain.repository.ArticleRepository;
import wattt3.realworld.article.domain.repository.TagRepository;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

@Service
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

    @Transactional
    public void createArticle(CreateArticleRequest request, Long userId) {
        User user = userRepository.getById(userId);

        Article article = new Article(request.title(), request.title(), request.description(),
                request.body(), Collections.emptyList(), Collections.emptyList(), userId);

        List<Tag> tags = request.tagList().stream()
                .map(name -> new Tag(name, article))
                .toList();
        article.addTags(tags);

        articleRepository.save(article);

        ArticleDTO.of(article, false, AuthorDTO.of(user, false));
    }
}
