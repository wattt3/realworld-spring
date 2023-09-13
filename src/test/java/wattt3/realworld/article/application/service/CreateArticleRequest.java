package wattt3.realworld.article.application.service;

import java.util.List;

public record CreateArticleRequest(String title,
                                   String description,
                                   String body,
                                   List<String> tagList) {

}
