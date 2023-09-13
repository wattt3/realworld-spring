package wattt3.realworld.article.domain;

public class Tag {

    private final String name;
    private Long id;

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
