package wattt3.realworld.user.application.service;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import wattt3.realworld.common.security.TokenManager;

@Primary
public class StubTokenManager implements TokenManager {

    @Override
    public String generate(String email) {
        return "fakeToken";
    }

    @Override
    public boolean isValid(String token) {
        return false;
    }

    @Override
    public Authentication getAuthentication(String token) {
        return null;
    }
}
