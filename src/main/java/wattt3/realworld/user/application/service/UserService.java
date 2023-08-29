package wattt3.realworld.user.application.service;

import wattt3.realworld.user.application.request.UserRegisterRequest;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.infra.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(UserRegisterRequest request) {
        User user = request.toDomain();
        userRepository.save(user);
    }
}