package wattt3.realworld.article.application.request;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@JsonTypeName("article")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public record CreateArticleRequest(@NotBlank String title,
                                   @NotBlank String description,
                                   @NotBlank String body,
                                   List<String> tagList) {

}
