package wattt3.realworld.common.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
