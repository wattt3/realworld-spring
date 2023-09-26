package wattt3.realworld.article.application.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;

@JsonRootName("comment")
public record AddCommentRequest(@NotBlank String body) {

}
