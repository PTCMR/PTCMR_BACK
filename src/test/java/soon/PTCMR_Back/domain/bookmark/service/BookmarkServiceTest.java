package soon.PTCMR_Back.domain.bookmark.service;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soon.PTCMR_Back.domain.bookmark.entity.Bookmark;
import soon.PTCMR_Back.domain.bookmark.repository.BookmarkRepository;
import soon.PTCMR_Back.domain.member.MockUser;
import soon.PTCMR_Back.domain.member.repository.MemberRepository;
import soon.PTCMR_Back.domain.team.data.TeamData;
import soon.PTCMR_Back.domain.team.repository.TeamRepository;

@SpringBootTest
class BookmarkServiceTest {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Long teamId;

    @Autowired
    private BookmarkRepository bookmarkRepository;

/*    @BeforeEach
    void setUp() {
        teamId = teamRepository.save(TeamData.createTeam());
        memberRepository.save(MockUser.createMockMember(MockUser.USER_UUID));
    }*/

    @Test
    @DisplayName("북마크 생성 성공")
    void create_validRequest_returnsId(){
        Long result = bookmarkService.create(MockUser.USER_UUID, teamId);
        System.out.println(result);
        Bookmark bookmark = bookmarkRepository.findById(result);

        assertThat(bookmark).isNotNull();
        assertThat(bookmark.getId()).isEqualTo(result);
    }
}