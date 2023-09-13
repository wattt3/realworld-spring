package wattt3.realworld.common;

import wattt3.realworld.article.application.service.ArticleApi;
import wattt3.realworld.profile.api.ProfileApi;
import wattt3.realworld.user.api.UserApi;

public class Scenario {

    public static UserApi userApi() {
        return new UserApi();
    }

    public static ProfileApi profileApi() {
        return new ProfileApi();
    }

    public static ArticleApi articleApi() {
        return new ArticleApi();
    }

}
