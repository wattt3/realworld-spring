package wattt3.realworld.article.application.response;

import java.util.List;
import wattt3.realworld.article.application.dto.CommentDTO;

public record MultipleCommentResponse(List<CommentDTO> comments) {

}
