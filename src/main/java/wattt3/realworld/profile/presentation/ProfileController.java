package wattt3.realworld.profile.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wattt3.realworld.common.security.dto.CustomUserDetails;
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
    public ProfileResponse getProfile(@PathVariable String followee, @AuthenticationPrincipal CustomUserDetails user) {
        return profileService.getProfile(followee, user != null ? user.getId() : null);
    }

    @PostMapping("/{followee}/follow")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse follow(@PathVariable String followee, @AuthenticationPrincipal CustomUserDetails user) {
        return profileService.follow(followee, user.getId());
    }

    @DeleteMapping("/{followee}/follow")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse unfollow(@PathVariable String followee, @AuthenticationPrincipal CustomUserDetails user) {
        return profileService.unfollow(followee, user.getId());
    }

}
