package wattt3.realworld.user.application.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import wattt3.realworld.user.domain.User;

@JsonTypeName("user")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public record UserResponse(
        String email,
        String token,
        String username,
        String bio,
        String image) {

    public static UserResponse of(User user, String token) {
        return new UserResponse(user.getEmail(),
                token,
                user.getUsername(),
                user.getBio(),
                user.getImage());
    }

}
