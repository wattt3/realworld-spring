package wattt3.realworld.user.application.request;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.Email;

@JsonTypeName("user")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public record UpdateUserRequest(@Email(message = "이메일 형식이 올바르지 않습니다.") String email,
                                String bio,
                                String image) {

}
