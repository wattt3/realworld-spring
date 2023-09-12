package wattt3.realworld.common.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetailsService;
import wattt3.realworld.common.security.dto.CustomUserDetails;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static wattt3.realworld.user.fixture.UserFixture.aUser;

class DefaultUserDetailsServiceTest {

    private final String emailFixture = "name@domain.com";
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        UserRepository stubUserRepository = new StubUserRepository();
        userDetailsService = new DefaultUserDetailsService(stubUserRepository);
    }

    @Test
    @DisplayName("loadByUsername 테스트")
    void loadByUsername() {
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(emailFixture);

        assertAll(() -> assertThat(userDetails.getUsername()).isEqualTo("username"),
                () -> assertThat(userDetails.getEmail()).isEqualTo(emailFixture));
    }

    @Test
    @DisplayName("존재하지 않는 유저 load")
    void loadByNotExistUsername() {
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("notExist@email.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("email: notExist@email.com 존재하지 않습니다.");
    }

    private class StubUserRepository implements UserRepository {

        @Override
        public User save(User user) {
            return null;
        }

        @Override
        public User getById(Long userId) {
            return null;
        }

        @Override
        public User getByEmail(String email) {
            if (email.equals("notExist@email.com")) {
                throw new IllegalArgumentException("email: %s 존재하지 않습니다.".formatted(email));
            }
            return aUser().build();
        }

        @Override
        public boolean existsByEmailOrUsername(String email, String username) {
            return false;
        }

        @Override
        public List<User> findAll() {
            return null;
        }

        @Override
        public User getByUsername(String username) {
            return null;
        }
    }

}
