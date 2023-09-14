package wattt3.realworld.article.domain.repository;

import java.util.List;
import wattt3.realworld.article.domain.Tag;

public interface TagRepository {

    List<Tag> findAll();
}
