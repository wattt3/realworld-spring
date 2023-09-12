package wattt3.realworld.user.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wattt3.realworld.common.ApiTest;
import wattt3.realworld.common.Scenario;
import wattt3.realworld.user.domain.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class UserApiTest extends ApiTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 등록(회원가입)")
    void registerUser() {
        Scenario.userApi().registerUserApi();

        assertThat(userRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("유저 등록 중복 예외")
    void registerDuplicateUser() {
        Scenario.userApi().registerUserApi()
                .userApi().registerDuplicateUserApi();
    }

    @Test
    @DisplayName("로그인")
    void login() {
        Scenario.userApi().registerUserApi()
                .userApi().loginApi();
    }

    @Test
    @DisplayName("현재 유저 정보")
    void getCurrentUser() {
        Scenario.userApi().registerUserApi()
                .userApi().getCurrentUserApi(tokenManager.generate(email));
    }

}
