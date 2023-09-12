package wattt3.realworld.user.domain;

import java.util.List;

public interface UserRepository {

    User save(User user);

    User getById(Long userId);

    User getByEmail(String email);

    User getByUsername(String username);

    boolean existsByEmailOrUsername(String email, String username);

    List<User> findAll();
}
