package wattt3.realworld.user.application.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import wattt3.realworld.common.security.TokenManager;
import wattt3.realworld.user.application.request.LoginUserRequest;
import wattt3.realworld.user.application.request.RegisterUserRequest;
import wattt3.realworld.user.application.request.UpdateUserRequest;
import wattt3.realworld.user.application.response.UserResponse;
import wattt3.realworld.user.domain.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(StubTokenManager.class)
class UserServiceTest {

    private String email;
    private String username;
    private String password;
    private String bio;
    private String image;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenManager tokenManager;

    @BeforeEach
    void setUp() {
        email = "name@domain.com";
        username = "username";
        password = "password";
        bio = "bio";
        image = "https://realworld.com/image.jpg";
    }

    @Test
    @DisplayName("register 패스워드 검사")
    void registerWithEncodedPassword() {
        userService.register(new RegisterUserRequest(email, username, password));

        assertThat(passwordEncoder.matches(password,
                userRepository.getByEmail(email).getPassword())).isTrue();
    }

    @Test
    @DisplayName("register 토큰 검사")
    void registerReturnToken() {
        UserResponse response = userService.register(
                new RegisterUserRequest(email, username, password));

        assertThat(response.token()).isEqualTo(tokenManager.generate(email));
    }

    @Test
    @DisplayName("로그인")
    void login() {
        LoginUserRequest request = new LoginUserRequest(email, password);

        userService.register(new RegisterUserRequest(email, username, password));
        UserResponse response = userService.login(request);

        assertThat(response)
                .usingRecursiveAssertion()
                .isEqualTo(
                        new UserResponse(email, tokenManager.generate(email), username, null, null));
    }

    @Test
    @DisplayName("로그인 예외 : 틀린 비밀번호")
    void loginIllegalPassword() {
        LoginUserRequest request = new LoginUserRequest(email, "illegalPassword");

        userService.register(new RegisterUserRequest(email, username, password));
        assertThatThrownBy(() -> userService.login(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("틀린 비밀번호입니다.");
    }

    @Test
    @DisplayName("유저 수정")
    void updateUser() {
        var request = new UpdateUserRequest("updatedEmail", "updatedBio",
                "https://realworld.com/updatedImage.jpg");
        var expected = new UserResponse("updatedEmail", tokenManager.generate("updatedEmail"),
                username, "updatedBio", "https://realworld.com/updatedImage.jpg");

        userService.register(new RegisterUserRequest(email, username, password));

        UserResponse response = userService.updateUser(userRepository.getByEmail(email).getId(), request);

        assertThat(response)
                .usingRecursiveAssertion()
                .isEqualTo(expected);
    }

}
