package wattt3.realworld.article.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import wattt3.realworld.common.entity.BaseTimeEntity;
import wattt3.realworld.user.domain.User;

@Table(name = "comment")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "article_id", nullable = false)
    private Long articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    public Comment(String body, Long articleId, User author) {
        Assert.hasText(body, "댓글 내용은 필수입니다.");
        Assert.notNull(articleId, "게시글은 필수입니다.");
        Assert.notNull(author, "댓글 작성자는 필수입니다.");

        this.body = body;
        this.articleId = articleId;
        this.author = author;
    }

}
