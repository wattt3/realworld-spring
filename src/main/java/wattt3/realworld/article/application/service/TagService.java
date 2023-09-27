package wattt3.realworld.article.application.service;

import java.util.List;
import org.springframework.stereotype.Service;
import wattt3.realworld.article.application.response.TagsResponse;
import wattt3.realworld.article.domain.Tag;
import wattt3.realworld.article.domain.repository.TagRepository;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagsResponse getTags() {
        List<String> tags = tagRepository.findAll().stream()
                .map(Tag::getName)
                .toList();

        return new TagsResponse(tags);
    }
}
