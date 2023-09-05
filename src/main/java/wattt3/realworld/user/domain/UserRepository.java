package wattt3.realworld.user.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailOrUsername(String email, String username);

    List<User> findAll();

    Optional<User> findByUsername(String username);
}
