package wattt3.realworld.article.domain;

public class TagFixture {

    private Long id = 1L;
    private String name = "tag";
    private Article article = null;

    public static TagFixture aTag() {
        return new TagFixture();
    }

    public TagFixture id(Long id) {
        this.id = id;
        return this;
    }

    public TagFixture name(String name) {
        this.name = name;
        return this;
    }

    public TagFixture article(Article article) {
        this.article = article;
        return this;
    }

    public Tag build() {
        return new Tag(id, name, article);
    }

}
