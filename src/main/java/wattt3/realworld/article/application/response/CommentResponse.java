package wattt3.realworld.article.application.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.time.LocalDateTime;
import wattt3.realworld.article.application.dto.AuthorDTO;
import wattt3.realworld.article.domain.Comment;

@JsonRootName("comment")
public record CommentResponse(Long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                              String body, AuthorDTO author) {

    public static CommentResponse of(Comment comment, boolean following) {
        return new CommentResponse(comment.getId(), comment.getCreatedAt(), comment.getUpdatedAt(),
                comment.getBody(), AuthorDTO.of(comment.getAuthor(), following));
    }
}
