package wattt3.realworld.article.application.response;

import java.util.List;
import wattt3.realworld.article.application.dto.ArticleDTO;

public record MultipleArticleResponse(List<ArticleDTO> articles, int articlesCount) {

}
