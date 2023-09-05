package wattt3.realworld.profile.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wattt3.realworld.profile.application.response.ProfileResponse;
import wattt3.realworld.profile.application.service.ProfileService;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/{followee}/follow")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse follow(@PathVariable String followee,
        @AuthenticationPrincipal User user) {
        return profileService.follow(followee, user.getUsername());
    }

}
