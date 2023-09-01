package wattt3.realworld.user.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wattt3.realworld.common.exception.CommonException;
import wattt3.realworld.common.exception.ErrorCode;
import wattt3.realworld.user.application.request.RegisterUserRequest;
import wattt3.realworld.user.application.response.UserResponse;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse register(RegisterUserRequest request) {
        userRepository.findByEmailOrUsername(request.email(), request.username())
            .ifPresent(user -> {
                throw new CommonException(ErrorCode.DUPLICATE_USER);
            });
        User user = request.toDomain();
        userRepository.save(user);
        return new UserResponse(user.getEmail(), null, user.getUsername(), user.getBio(),
            user.getImage());
    }
}
