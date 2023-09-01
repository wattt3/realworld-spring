package wattt3.realworld.user.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wattt3.realworld.common.exception.CommonException;
import wattt3.realworld.common.exception.ErrorCode;
import wattt3.realworld.common.security.JwtTokenManager;
import wattt3.realworld.user.application.request.RegisterUserRequest;
import wattt3.realworld.user.application.response.UserResponse;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenManager jwtTokenManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
        JwtTokenManager jwtTokenManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Transactional
    public UserResponse register(RegisterUserRequest request) {
        userRepository.findByEmailOrUsername(request.email(), request.username())
            .ifPresent(user -> {
                throw new CommonException(ErrorCode.DUPLICATE_USER);
            });

        User user = User.builder()
            .email(request.email())
            .username(request.username())
            .password(passwordEncoder.encode(request.password()))
            .build();

        userRepository.save(user);

        return new UserResponse(user.getEmail(),
            jwtTokenManager.generate(user.getEmail()),
            user.getUsername(),
            user.getBio(),
            user.getImage());
    }
}
