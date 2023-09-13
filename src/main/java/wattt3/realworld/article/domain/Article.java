package wattt3.realworld.article.domain;

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

    public Article(
            String slug,
            String title,
            String description,
            String body,
            List<Tag> tags,
            List<Long> favoriteUserIds,
            Long authorId) {
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
}
