package wattt3.realworld.user.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import wattt3.realworld.user.domain.User;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmailOrUsername(String email, String username);
}
