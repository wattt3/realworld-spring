package wattt3.realworld.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import wattt3.realworld.common.exception.CommonException;
import wattt3.realworld.common.exception.ErrorCode;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

@Component
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DefaultUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> {
                throw new CommonException(ErrorCode.NOT_FOUND_USER,
                    "존재하지 않는 유저입니다. email : %s".formatted(email));
            });

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .build();
    }
}
