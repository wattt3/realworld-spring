package wattt3.realworld.profile.application.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import wattt3.realworld.user.domain.User;

@JsonTypeName("profile")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public record ProfileResponse(String username, String bio, String image, boolean following) {

    public static ProfileResponse of(User user, boolean following) {
        return new ProfileResponse(user.getUsername(), user.getBio(), user.getImage(), following);
    }

}
