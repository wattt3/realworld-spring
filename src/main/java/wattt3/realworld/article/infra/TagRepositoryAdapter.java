package wattt3.realworld.article.infra;

import java.util.List;
import org.springframework.stereotype.Repository;
import wattt3.realworld.article.domain.Tag;
import wattt3.realworld.article.domain.repository.TagRepository;

@Repository
public class TagRepositoryAdapter implements TagRepository {

    private final JpaTagRepository jpaTagRepository;

    public TagRepositoryAdapter(JpaTagRepository jpaTagRepository) {
        this.jpaTagRepository = jpaTagRepository;
    }

    @Override
    public List<Tag> findAll() {
        return jpaTagRepository.findAll();
    }
}
