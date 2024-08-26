package soon.PTCMR_Back.domain.team.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.transaction.Transactional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.member.entity.SocialType;
import soon.PTCMR_Back.domain.member.repository.MemberRepository;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamCreateRequest;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamUpdateRequest;
import soon.PTCMR_Back.domain.team.dto.response.TeamDetails;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.repository.TeamJpaRepository;
import soon.PTCMR_Back.global.exception.InvalidMemberException;
import soon.PTCMR_Back.global.exception.TeamNotFoundException;
import soon.PTCMR_Back.global.oauth.dto.UserDTO;
import soon.PTCMR_Back.global.util.invite.InviteGenerator;

@SpringBootTest
@DisplayName("TeamService 클래스")
@Transactional
class TeamServiceTest {

	@Autowired
	private TeamService teamService;
	@Autowired
	private TeamJpaRepository teamRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private InviteGenerator inviteGenerator;

	@Nested
	@DisplayName("create 메서드는")
	class Describe_create {

		UserDTO user = UserDTO.builder()
			.name("test")
			.provider(SocialType.KAKAO)
			.uuid("KAKAO 12345678")
			.build();


		@BeforeEach
		@Rollback(value = false)
		void setUp() {
			memberRepository.save(
				Member.create(user.uuid(), user.name(), "default", SocialType.KAKAO));
		}

		@AfterEach
		void tearDown() {
			memberRepository.deleteAll();
		}

		@Nested
		@DisplayName("만약 성공한다면")
		class Create_success {

			@Test
			@DisplayName("팀의 아이디를 반환한다.")
			void success() {
				TeamCreateRequest teamCreateRequest = new TeamCreateRequest("test");

				Long id = teamService.create(user.uuid(), teamCreateRequest.title());

				Team result = teamRepository.findById(id).orElseThrow();

				assertThat(result).isNotNull();
				assertThat(result.getId()).isEqualTo(id);
				assertThat(result.getInviteCode()).isNotNull();
				assertThat(result.getTitle()).isEqualTo(teamCreateRequest.title());
				assertThat(result.getCreateTime()).isNotNull();
				assertThat(result.getSchedule().getDay()).isEqualTo(7);
				assertThat(result.getSchedule().getHour()).isEqualTo(12);
			}
		}

		@Nested
		@DisplayName("만약 10000번 호출했을때")
		@Rollback(false)
		class Create_Bulk_Request{

			@Test
			@DisplayName("성공한다면")
			void success(){
				int threads = 10;
				ExecutorService es = Executors.newFixedThreadPool(threads);

				for(int i = 0 ; i < threads ; i++){
					int index = i;
					es.submit(() ->{
						teamService.create(user.uuid(), "title" + index);
					});
				}
			}
		}
	}


	@Nested
	@DisplayName("update 메서드는")
	class Describe_update {

		@Nested
		@DisplayName("올바른 요청을 받았을 때")
		class Update_success {

			@BeforeEach
			void setUp() {
				teamRepository.save(Team.create("test", inviteGenerator.createInviteCode()));
			}

			@Test
			@DisplayName("TeamDetail 반환한다")
			void success() {

				TeamUpdateRequest teamUpdateRequest = new TeamUpdateRequest(
					5L,
					"newTestTitle",
					10L,
					12
				);

				String uuid = "kakao 1234";

				TeamDetails result = teamService.update(
					teamUpdateRequest.teamId(),
					teamUpdateRequest.newTitle(),
					teamUpdateRequest.notificationDay(),
					teamUpdateRequest.notificationHour(),
					uuid);

				assertThat(result.teamId()).isEqualTo(teamUpdateRequest.teamId());
				assertThat(result.title()).isEqualTo(teamUpdateRequest.newTitle());
				assertThat(result.notificationDay()).isEqualTo(teamUpdateRequest.notificationDay());
				assertThat(result.notificationHour()).isEqualTo(
					teamUpdateRequest.notificationHour());

			}
		}

		@Nested
		@DisplayName("찾는 팀이 존재하지 않을 때")
		class Update_InvalidId_Expect_TeamNotFoundException {

			@Test
			@DisplayName("TeamNotFoundException 발생시킨다")
			void fail() {
				assertThatThrownBy(
					() -> teamService.update(999L, "test", 1L, 1L, "test")
				).isInstanceOf(TeamNotFoundException.class);
			}
		}
	}


	@Nested
	@DisplayName("delete 메서드는")
	class Describe_delete{

		@Nested
		@DisplayName("올바른 요청을 받았을 때")
		class Delete_success {

			@Test
			@DisplayName("deleted를 true로 바꾼다.")
			void success() {

				Long teamId = 2L;
				String uuid = "kakao 1234";

				teamService.delete(
					teamId,
					uuid
				);

				assertThat(teamRepository.findById(teamId)).isEmpty();
			}
		}


		@Nested
		@DisplayName("팀에 속하지 않은 멤버가 요청했을 때")
		class Delete_InvalidMember_Expect_InvalidMemberException {

			@Test
			@DisplayName("InvalidMemberException 발생시킨다.")
			void fail() {
				assertThatThrownBy(
					() -> teamService.delete(2L, "kakao 12345")
				).isInstanceOf(InvalidMemberException.class);
			}
		}


		@Nested
		@DisplayName("삭제하려는 팀이 존재하지 않을 때")
		class Delete_InvalidTeam_Expect_TeamNotFoundException {

			@Test
			@DisplayName("TeamNotFoundException 발생시킨다.")
			void fail() {
				assertThatThrownBy(
					() -> teamService.delete(999L, "kakao 12345")
				).isInstanceOf(TeamNotFoundException.class);
			}
		}
	}

}