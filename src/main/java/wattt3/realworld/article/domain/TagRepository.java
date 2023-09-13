package wattt3.realworld.article.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagRepository {

    private final Map<Long, Tag> tags = new HashMap<>();
    private Long sequence = 1L;


    public List<Tag> findAll() {
        return new ArrayList<>(tags.values());
    }

    public boolean existsByName(String name) {
        return tags.values().stream()
                .anyMatch(tag -> tag.getName().equals(name));
    }

    public Tag getByName(String name) {
        return tags.values().stream()
                .filter(tag -> tag.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    public Tag save(Tag tag) {
        tag.setId(sequence);
        ++sequence;
        tags.put(tag.getId(), tag);
        return tag;
    }
}
