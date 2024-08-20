package soon.PTCMR_Back.domain.team.service;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.member.entity.SocialType;
import soon.PTCMR_Back.domain.member.repository.MemberRepository;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamCreateRequest;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.repository.TeamRepository;
import soon.PTCMR_Back.global.oauth.dto.UserDTO;

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
	class Describe_create {

		UserDTO user = UserDTO.builder()
			.name("test")
			.provider(SocialType.KAKAO)
			.uuid("KAKAO 12345678")
			.build();


		@BeforeEach
		void setUp() {
			memberRepository.save(
				Member.create(user.uuid(), user.name(), "default", SocialType.KAKAO));
		}

		@Nested
		@DisplayName("만약 성공한다면")
		class Create_success {

			@Test
			@DisplayName("팀의 아이디를 반환한다.")
			@Transactional
			void success() {
				TeamCreateRequest teamCreateRequest = new TeamCreateRequest("test");

				Long id = teamService.create(user.uuid(), teamCreateRequest.title());

				Team result = teamRepository.findById(id);

				assertThat(result).isNotNull();
				assertThat(result.getId()).isEqualTo(id);
				assertThat(result.getTitle()).isEqualTo(teamCreateRequest.title());
				assertThat(result.getCreateTime()).isNotNull();
				assertThat(result.getSchedule().getDay()).isEqualTo(7);
				assertThat(result.getSchedule().getHour()).isEqualTo(12);
			}
		}
	}

}