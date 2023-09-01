package wattt3.realworld.user.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import wattt3.realworld.common.security.JwtTokenManager;
import wattt3.realworld.user.application.request.RegisterUserRequest;
import wattt3.realworld.user.application.response.UserResponse;
import wattt3.realworld.user.domain.UserRepository;

@SpringBootTest
@Transactional
class UserServiceTest {

    private String email;
    private String username;
    private String password;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenManager jwtTokenManager;

    @BeforeEach
    void setUp() {
        email = "name@domain.com";
        username = "username";
        password = "password";
    }

    @Test
    @DisplayName("register 패스워드 검사")
    void registerWithEncodedPassword() {
        userService.register(new RegisterUserRequest(email, username, password));

        assertThat(passwordEncoder.matches(password,
            userRepository.findByEmail(email).get().getPassword()))
            .isTrue();
    }

    @Test
    @DisplayName("register 토큰 검사")
    void registerReturnToken() {
        UserResponse response = userService.register(
            new RegisterUserRequest(email, username, password));

        assertThat(response.token()).isEqualTo(jwtTokenManager.generate(email));
    }

    @Test
    void login() {
        LoginUserRequest request = new LoginUserRequest(email, password);
    }

    public record LoginUserRequest(
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,
        @NotBlank(message = "패스워드는 필수입니다.")
        String password) {

    }
}
