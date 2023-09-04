package wattt3.realworld.common.security;

import org.springframework.security.core.Authentication;

public interface TokenManager {

    String generate(String email);

    boolean isValid(String token);

    Authentication getAuthentication(String token);

}
