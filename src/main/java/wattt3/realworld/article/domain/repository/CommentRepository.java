package wattt3.realworld.article.domain.repository;

import wattt3.realworld.article.domain.Comment;

public interface CommentRepository {

    Comment save(Comment comment);
}
