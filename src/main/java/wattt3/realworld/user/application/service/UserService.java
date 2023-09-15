package wattt3.realworld.user.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wattt3.realworld.common.exception.CommonException;
import wattt3.realworld.common.exception.ErrorCode;
import wattt3.realworld.common.security.TokenManager;
import wattt3.realworld.user.application.request.LoginUserRequest;
import wattt3.realworld.user.application.request.RegisterUserRequest;
import wattt3.realworld.user.application.request.UpdateUserRequest;
import wattt3.realworld.user.application.response.UserResponse;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            TokenManager tokenManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenManager = tokenManager;
    }

    @Transactional
    public UserResponse register(RegisterUserRequest request) {
        if (userRepository.existsByEmailOrUsername(request.email(), request.username())) {
            throw new CommonException(ErrorCode.DUPLICATE_USER);
        }

        User user = User.builder()
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(user);

        return UserResponse.of(user, tokenManager.generate(user.getEmail()));
    }

    @Transactional(readOnly = true)
    public UserResponse login(LoginUserRequest request) {
        User user = userRepository.getByEmail(request.email());

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("틀린 비밀번호입니다.");
        }

        return UserResponse.of(user, tokenManager.generate(user.getEmail()));
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long userId) {
        User user = userRepository.getById(userId);

        return UserResponse.of(user, tokenManager.generate(user.getEmail()));
    }

    @Transactional
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.getById(userId);

        user.update(request.email(), request.bio(), request.image());

        return UserResponse.of(user, tokenManager.generate(user.getEmail()));
    }

}
