package wattt3.realworld.article.application.response;

import wattt3.realworld.article.application.dto.CommentDTO;
import wattt3.realworld.article.domain.Comment;

public record SingleCommentResponse(CommentDTO comment) {

    public static SingleCommentResponse of(Comment comment, boolean following) {
        return new SingleCommentResponse(CommentDTO.of(comment, following));
    }
}
