package wattt3.realworld.article.application.dto;

import java.time.LocalDateTime;
import wattt3.realworld.article.domain.Comment;

public record CommentDTO(Long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                         String body, AuthorDTO author) {

    public static CommentDTO of(Comment comment, boolean following) {
        return new CommentDTO(comment.getId(), comment.getCreatedAt(), comment.getUpdatedAt(),
                comment.getBody(), AuthorDTO.of(comment.getAuthor(), following));
    }
}
