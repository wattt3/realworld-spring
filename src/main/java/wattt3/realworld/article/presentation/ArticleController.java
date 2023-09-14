package wattt3.realworld.article.presentation;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wattt3.realworld.article.application.request.CreateArticleRequest;
import wattt3.realworld.article.application.request.UpdateArticleRequest;
import wattt3.realworld.article.application.response.MultipleArticleResponse;
import wattt3.realworld.article.application.response.SingleArticleResponse;
import wattt3.realworld.article.application.service.ArticleService;
import wattt3.realworld.common.security.dto.CustomUserDetails;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{slug}")
    @ResponseStatus(HttpStatus.OK)
    public SingleArticleResponse getArticle(@PathVariable String slug,
            @AuthenticationPrincipal CustomUserDetails user) {
        return articleService.getArticle(slug, user != null ? user.getId() : null);
    }

    @GetMapping("/feed")
    @ResponseStatus(HttpStatus.OK)
    public MultipleArticleResponse getFeedArticles(
            @RequestParam(defaultValue = "0") final int offset,
            @RequestParam(defaultValue = "20") final int limit,
            @AuthenticationPrincipal CustomUserDetails user) {
        PageRequest pageable = PageRequest.of(offset, limit, Sort.by(Direction.DESC, "createdAt"));
        return articleService.getFeedArticles(pageable, user.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SingleArticleResponse createArticle(@RequestBody CreateArticleRequest request,
            @AuthenticationPrincipal CustomUserDetails user) {
        return articleService.createArticle(request, user.getId());
    }

    @PutMapping("/{slug}")
    @ResponseStatus(HttpStatus.OK)
    public SingleArticleResponse updateArticle(@RequestBody UpdateArticleRequest request,
            @PathVariable String slug, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return articleService.updateArticle(request, slug, userDetails.getId());
    }

    @DeleteMapping("/{slug}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteArticle(@PathVariable String slug,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        articleService.deleteArticle(slug, userDetails.getId());
    }
}
