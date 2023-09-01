package wattt3.realworld.user.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import wattt3.realworld.user.application.request.RegisterUserRequest;
import wattt3.realworld.user.domain.UserRepository;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Password Encode")
    void registerWithEncodedPassword() {
        String password = "password";
        
        userService.register(new RegisterUserRequest("name@domain.com", "username", password));

        assertThat(passwordEncoder.matches(password,
            userRepository.findByEmail("name@domain.com").get().getPassword()))
            .isTrue();
    }
}
