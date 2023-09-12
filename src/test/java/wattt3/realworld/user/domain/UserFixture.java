package wattt3.realworld.user.domain;

public class UserFixture {

    private Long id = 1L;
    private String email = "name@domain.com";
    private String username = "username";
    private String password = "password";
    private String bio = null;
    private String image = null;

    public static UserFixture aUser() {
        return new UserFixture();
    }

    public UserFixture id(final Long id) {
        this.id = id;
        return this;
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
        return new User(id, email, username, password, bio, image);
    }
}
