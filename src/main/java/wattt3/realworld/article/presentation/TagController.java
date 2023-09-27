package wattt3.realworld.article.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wattt3.realworld.article.application.response.TagsResponse;
import wattt3.realworld.article.application.service.TagService;

@RestController
@RequestMapping("tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public TagsResponse getTags() {
        return tagService.getTags();
    }
}
