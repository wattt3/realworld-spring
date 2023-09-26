package wattt3.realworld.article.domain;

import static wattt3.realworld.article.domain.ArticleFixture.aArticle;
import static wattt3.realworld.user.domain.UserFixture.aUser;

import wattt3.realworld.user.domain.User;

public class CommentFixture {

    private Long id = 1L;
    private String body = "body";
    private Article article = aArticle().build();
    private User author = aUser().build();

    public static CommentFixture aComment() {
        return new CommentFixture();
    }
    
    public CommentFixture id(Long id) {
        this.id = id;
        return this;
    }

    public CommentFixture body(String body) {
        this.body = body;
        return this;
    }

    public CommentFixture article(Article article) {
        this.article = article;
        return this;
    }

    public CommentFixture author(User author) {
        this.author = author;
        return this;
    }

    public Comment build() {
        return new Comment(id, body, article, author);
    }

}
