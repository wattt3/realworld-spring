package wattt3.realworld.article.domain.repository;

import java.util.List;
import wattt3.realworld.article.domain.Comment;

public interface CommentRepository {

    Comment save(Comment comment);

    List<Comment> getByArticleId(Long articleId);

    void delete(Comment comment);

    Comment getById(Long commentId);
}
