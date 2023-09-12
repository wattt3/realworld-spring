package wattt3.realworld.common.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtTokenManagerTest {

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Test
    void validToken() {
        String token = jwtTokenManager.generate("realworld1@email.com");

        assertThat(jwtTokenManager.isValid(token)).isTrue();
    }
}
