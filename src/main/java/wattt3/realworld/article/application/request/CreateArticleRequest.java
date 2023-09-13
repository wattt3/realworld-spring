package wattt3.realworld.article.application.request;

import java.util.List;

public record CreateArticleRequest(String title,
                                   String description,
                                   String body,
                                   List<String> tagList) {

}
