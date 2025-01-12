package wattt3.realworld.user.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wattt3.realworld.common.security.dto.CustomUserDetails;
import wattt3.realworld.user.application.request.LoginUserRequest;
import wattt3.realworld.user.application.request.RegisterUserRequest;
import wattt3.realworld.user.application.response.UserResponse;
import wattt3.realworld.user.application.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@RequestBody RegisterUserRequest request) {
        return userService.register(request);
    }

    @PostMapping("/users/login")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse login(@RequestBody LoginUserRequest request) {
        return userService.login(request);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser(@AuthenticationPrincipal CustomUserDetails user) {
        return userService.getUser(user.getId());
    }
}
