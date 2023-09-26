package wattt3.realworld.article.infra;

import java.util.List;
import org.springframework.stereotype.Repository;
import wattt3.realworld.article.domain.Comment;
import wattt3.realworld.article.domain.repository.CommentRepository;

@Repository
public class CommentRepositoryAdapter implements CommentRepository {

    private final JpaCommentRepository jpaCommentRepository;

    public CommentRepositoryAdapter(JpaCommentRepository jpaCommentRepository) {
        this.jpaCommentRepository = jpaCommentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return jpaCommentRepository.save(comment);
    }

    @Override
    public List<Comment> getByArticleId(Long articleId) {
        return jpaCommentRepository.findByArticleId(articleId);
    }
}
