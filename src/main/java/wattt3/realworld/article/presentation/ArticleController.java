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
import wattt3.realworld.article.application.request.AddCommentRequest;
import wattt3.realworld.article.application.request.CreateArticleRequest;
import wattt3.realworld.article.application.request.UpdateArticleRequest;
import wattt3.realworld.article.application.response.MultipleArticleResponse;
import wattt3.realworld.article.application.response.SingleArticleResponse;
import wattt3.realworld.article.application.service.ArticleService;
import wattt3.realworld.article.domain.condition.ArticleSearchCondition;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MultipleArticleResponse getArticles(
            @RequestParam(required = false) final String tag,
            @RequestParam(required = false) final String author,
            @RequestParam(required = false) final String favorited,
            @RequestParam(defaultValue = "0") final int offset,
            @RequestParam(defaultValue = "20") final int limit,
            @AuthenticationPrincipal CustomUserDetails user) {
        PageRequest pageable = PageRequest.of(offset, limit);
        ArticleSearchCondition condition = new ArticleSearchCondition(tag, author, favorited);

        return articleService.getArticles(condition, pageable, user != null ? user.getId() : null);
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

    @GetMapping("/{slug}/comments")
    @ResponseStatus(HttpStatus.OK)
    public void getComments(@PathVariable String slug,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        articleService.getComments(slug, userDetails != null ? userDetails.getId() : null);
    }

    @PostMapping("/{slug}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public void addComment(@RequestBody AddCommentRequest request, @PathVariable String slug,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        articleService.addComment(request, slug, userDetails.getId());
    }

    @DeleteMapping("/{slug}/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        articleService.deleteComment(id, userDetails.getId());
    }

    @PostMapping("/{slug}/favorite")
    @ResponseStatus(HttpStatus.CREATED)
    public SingleArticleResponse favoriteArticle(@PathVariable String slug,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return articleService.favoriteArticle(slug, userDetails.getId());
    }

    @DeleteMapping("/{slug}/favorite")
    @ResponseStatus(HttpStatus.OK)
    public SingleArticleResponse unFavoriteArticle(@PathVariable String slug,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return articleService.unFavoriteArticle(slug, userDetails.getId());
    }
}
