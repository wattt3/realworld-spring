package wattt3.realworld.profile.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/{followee}")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse getProfile(@PathVariable String followee,
        @AuthenticationPrincipal User user) {
        String userEmail = user != null ? user.getUsername() : null;
        return profileService.getProfile(followee, userEmail);
    }

    @PostMapping("/{followee}/follow")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse follow(@PathVariable String followee,
        @AuthenticationPrincipal User user) {
        return profileService.follow(followee, user.getUsername());
    }

    @DeleteMapping("/{followee}/follow")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse unfollow(@PathVariable String followee,
        @AuthenticationPrincipal User user) {
        return profileService.unfollow(followee, user.getUsername());
    }

}
