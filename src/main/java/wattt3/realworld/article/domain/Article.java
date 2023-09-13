package wattt3.realworld.article.domain;

import com.google.common.annotations.VisibleForTesting;
import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wattt3.realworld.common.entity.BaseTimeEntity;

@Table(name = "article")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Article extends BaseTimeEntity {

    private static final Pattern SPACE = Pattern.compile(" ");
    private static final String DASH = "-";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;
    @Column(name = "title", nullable = false, unique = true)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "body", nullable = false)
    private String body;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags;
    @Column(name = "favorited")
    @ElementCollection
    private List<Long> favoriteUserIds;
    @Column(name = "authorId", nullable = false)
    private Long authorId;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    public Article(
            String title,
            String description,
            String body,
            List<Tag> tags,
            List<Long> favoriteUserIds,
            Long authorId) {
        Assert.hasText(title, "제목은 필수입니다.");
        Assert.hasText(description, "설명은 필수입니다.");
        Assert.hasText(body, "본문은 필수입니다.");
        Assert.notNull(authorId, "작성자는 필수입니다.");

        this.slug = SPACE.matcher(title).replaceAll(DASH);
        this.title = title;
        this.description = description;
        this.body = body;
        this.tags = tags;
        this.favoriteUserIds = favoriteUserIds;
        this.authorId = authorId;
    }

    @VisibleForTesting
    Article(
            Long id,
            String slug,
            String title,
            String description,
            String body,
            List<Tag> tags,
            List<Long> favoriteUserIds,
            Long authorId) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.tags = tags;
        this.favoriteUserIds = favoriteUserIds;
        this.authorId = authorId;
    }

    public void addTags(List<Tag> tags) {
        tags.stream()
                .forEach(tag -> tag.setAritcle(this));
        this.tags = tags;
    }

    public void update(String title, String description, String body) {
        if (title != null && !title.isEmpty()) {
            this.slug = SPACE.matcher(title).replaceAll(DASH);
            this.title = title;
        }
        if (description != null && !description.isEmpty()) {
            this.description = description;
        }
        if (body != null && !body.isEmpty()) {
            this.body = body;
        }
    }

    public boolean isAuthor(Long userId) {
        return authorId.equals(userId);
    }

    public void delete(Long userId) {
        if (!isAuthor(userId)) {
            throw new IllegalArgumentException(
                    "article slug: %s 의 작성자가 아닙니다.".formatted(this.slug));
        }
        isDeleted = true;
    }
}
