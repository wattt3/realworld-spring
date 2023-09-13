package wattt3.realworld.article.domain;

import wattt3.realworld.user.domain.User;

public record Author(String username, String bio, String image, boolean following) {

    public static Author of(User user, boolean following) {
        return new Author(user.getUsername(), user.getBio(), user.getImage(), following);
    }
}
