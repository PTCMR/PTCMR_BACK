package soon.PTCMR_Back.domain.team.service;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soon.PTCMR_Back.domain.member.repository.MemberRepository;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamCreateRequest;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.repository.TeamRepository;

@SpringBootTest
@DisplayName("TeamService 클래스")
class TeamServiceTest {

    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create{

        @Nested
        @DisplayName("만약 성공한다면")
        class Create_success{

            @Test
            @DisplayName("식별자값을 반환한다.")
            @Transactional
            void success(){
                TeamCreateRequest teamCreateRequest = new TeamCreateRequest("test");

                Long id = teamService.create(teamCreateRequest.title());

                Team result = teamRepository.findById(id);

                assertThat(result).isNotNull();
                assertThat(result.getId()).isEqualTo(1L);
                assertThat(result.getTitle()).isEqualTo(teamCreateRequest.title());
                assertThat(result.getCreateTime()).isNotNull();
                assertThat(result.getSchedule().getDay()).isEqualTo(7);
                assertThat(result.getSchedule().getHour()).isEqualTo(12);
            }
        }
    }

}