package wattt3.realworld.common.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static wattt3.realworld.user.fixture.UserFixture.aUser;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import wattt3.realworld.common.exception.CommonException;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

class DefaultUserDetailsServiceTest {

    private UserDetailsService userDetailsService;
    private String emailFixture;

    @BeforeEach
    void setUp() {
        UserRepository stubUserRepository = new StubUserRepository();
        userDetailsService = new DefaultUserDetailsService(stubUserRepository);
        emailFixture = "name@domain.com";
    }

    @Test
    @DisplayName("loadByUsername 테스트")
    void loadByUsername() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(emailFixture);

        assertAll(() -> assertThat(userDetails.getUsername()).isEqualTo(emailFixture),
            () -> assertThat(userDetails.getPassword()).isEqualTo("password"));
    }

    @Test
    @DisplayName("존재하지 않는 유저 load")
    void loadByNotExistUsername() {
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("notExist@email.com"))
            .isInstanceOf(CommonException.class)
            .hasMessage("존재하지 않는 유저입니다. email : notExist@email.com");
    }

    private class StubUserRepository implements UserRepository {

        @Override
        public User save(User user) {
            return null;
        }

        @Override
        public Optional<User> findByEmail(String email) {
            if (email.equals(emailFixture)) {
                return Optional.ofNullable(aUser()
                    .email(emailFixture)
                    .build());
            }
            return Optional.empty();
        }

        @Override
        public Optional<User> findByEmailOrUsername(String email, String username) {
            return Optional.empty();
        }

        @Override
        public List<User> findAll() {
            return null;
        }
    }

}
