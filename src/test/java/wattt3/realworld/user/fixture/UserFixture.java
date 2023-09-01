package wattt3.realworld.user.fixture;

import wattt3.realworld.user.domain.User;

public class UserFixture {

    private String email = "name@domain.com";
    private String username = "username";
    private String password = "password";
    private String bio = null;
    private String image = null;

    public static UserFixture aUser() {
        return new UserFixture();
    }

    public UserFixture email(final String email) {
        this.email = email;
        return this;
    }

    public UserFixture username(final String username) {
        this.username = username;
        return this;
    }

    public UserFixture password(final String password) {
        this.password = password;
        return this;
    }

    public UserFixture bio(final String bio) {
        this.bio = bio;
        return this;
    }

    public UserFixture image(final String image) {
        this.image = image;
        return this;
    }

    public User build() {
        return new User(email, username, password, bio, image);
    }
}
