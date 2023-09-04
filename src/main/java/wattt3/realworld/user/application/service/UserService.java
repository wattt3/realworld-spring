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

        return userToResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse login(LoginUserRequest request) {
        User user = getByEmail(request.email());

        validatePassword(request, user);

        return userToResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(String email) {
        User user = getByEmail(email);

        return userToResponse(user);
    }

    @Transactional
    public UserResponse updateUser(String email, UpdateUserRequest request) {
        User user = getByEmail(email);

        User updatedUser = user.update(request.email(), request.bio(), request.image());

        return userToResponse(updatedUser);
    }

    private UserResponse userToResponse(User user) {
        return new UserResponse(user.getEmail(),
            tokenManager.generate(user.getEmail()),
            user.getUsername(),
            user.getBio(),
            user.getImage());
    }

    private void validatePassword(LoginUserRequest request, User user) {
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("틀린 비밀번호입니다.");
        }
    }

    private User getByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> {
                throw new CommonException(ErrorCode.NOT_FOUND_USER,
                    "존재하지 않는 유저입니다. email : %s".formatted(email));
            });
    }
}
