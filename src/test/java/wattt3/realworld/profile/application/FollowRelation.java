package wattt3.realworld.profile.application;

public class FollowRelation {

    private final Long followeeId;

    private final Long followerId;

    public FollowRelation(Long followeeId, Long followerId) {
        this.followeeId = followeeId;
        this.followerId = followerId;
    }

    public Long getFolloweeId() {
        return followeeId;
    }

    public Long getFollowerId() {
        return followerId;
    }
}
