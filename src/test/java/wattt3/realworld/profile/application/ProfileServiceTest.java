package wattt3.realworld.profile.application;

import static org.assertj.core.api.Assertions.assertThat;
import static wattt3.realworld.user.fixture.UserFixture.aUser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wattt3.realworld.profile.application.service.ProfileService;
import wattt3.realworld.profile.domain.FollowRelationRepository;
import wattt3.realworld.user.domain.User;
import wattt3.realworld.user.domain.UserRepository;

@SpringBootTest
@Transactional
public class ProfileServiceTest {

    private final User followee = aUser().email("followee@domain.com")
        .username("followee")
        .build();

    @Autowired
    private ProfileService profileService;
    @Autowired
    private FollowRelationRepository followRelationRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void follow() {
        userRepository.save(aUser().build());
        userRepository.save(followee);

        profileService.follow("followee", "name@domain.com");

        assertThat(followRelationRepository.findAll()).hasSize(1);
    }
}
