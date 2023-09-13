package wattt3.realworld.article.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import wattt3.realworld.article.domain.Tag;

public interface JpaTagRepository extends JpaRepository<Tag, Long> {

}
