package wattt3.realworld.user.infra;

import org.springframework.stereotype.Repository;
import wattt3.realworld.common.exception.CommonException;
import wattt3.realworld.common.exception.ErrorCode;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

import java.util.List;

@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        return jpaUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("userId: %s 존재하지 않습니다.".formatted(id)));
    }

    @Override
    public User getByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("email: %s 존재하지 않습니다.".formatted(email)));
    }

    @Override
    public boolean existsByEmailOrUsername(String email, String username) {
        return jpaUserRepository.existsByEmailOrUsername(email, username);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll();
    }

    @Override
    public User getByUsername(String username) {
        return jpaUserRepository.findByUsername(username)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER,
                        "username : %s 존재하지 않습니다.".formatted(username)));
    }
}
