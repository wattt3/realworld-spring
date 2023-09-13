package wattt3.realworld.article.application.dto;

import wattt3.realworld.user.domain.User;

public record AuthorDTO(String username, String bio, String image, boolean following) {

    public static AuthorDTO of(User user, boolean following) {
        return new AuthorDTO(user.getUsername(), user.getBio(), user.getImage(), following);
    }
}
