package wattt3.realworld.profile.fixture;

import wattt3.realworld.profile.domain.FollowRelation;

public class ProfileFixture {

    private Long followeeId = 1L;
    private Long followerId = 2L;

    public static ProfileFixture aProfile() {
        return new ProfileFixture();
    }

    public ProfileFixture followeeId(Long followeeId) {
        this.followeeId = followeeId;
        return this;
    }

    public ProfileFixture followerId(Long followerId) {
        this.followerId = followerId;
        return this;
    }

    public FollowRelation build() {
        return new FollowRelation(followeeId, followerId);
    }
}
