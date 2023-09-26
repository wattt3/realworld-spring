package wattt3.realworld.article.infra;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wattt3.realworld.article.domain.Comment;

public interface JpaCommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByArticleId(Long articleId);

}
