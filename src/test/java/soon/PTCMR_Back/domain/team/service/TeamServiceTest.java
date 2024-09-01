package soon.PTCMR_Back.domain.team.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soon.PTCMR_Back.domain.member.MockUser;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.member.repository.MemberRepository;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamCreateRequest;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamUpdateRequest;
import soon.PTCMR_Back.domain.team.dto.response.TeamDetails;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.entity.TeamMember;
import soon.PTCMR_Back.domain.team.repository.TeamJpaRepository;
import soon.PTCMR_Back.domain.team.repository.TeamMemberRepository;
import soon.PTCMR_Back.global.exception.InvalidMemberException;
import soon.PTCMR_Back.global.exception.TeamNotFoundException;
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
	private TeamMemberRepository teamMemberRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private InviteGenerator inviteGenerator;

	private  Member member;
	private Long id;

	@BeforeEach
	 void beforeAll() {
		member = memberRepository.save(MockUser.createMockMember("kakao 1234"));
	}


	@Nested
	@DisplayName("create 메서드는")
	class Describe_create {

		@Nested
		@DisplayName("올바른 요청을 받았을 때")
		class Create_success {

			@Test
			@DisplayName("팀의 아이디를 반환한다.")
			void success() {
				TeamCreateRequest teamCreateRequest = new TeamCreateRequest("test");

				id = teamService.create(member.getUuid(), teamCreateRequest.title());

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
	}


	@Nested
	@DisplayName("update 메서드는")
	class Describe_update {

		@Nested
		@DisplayName("올바른 요청을 받았을 때")
		class Update_success {

			@BeforeEach
			void setUp() {
				Team team = teamRepository.save(
					Team.create("test", inviteGenerator.createInviteCode()));

				id = team.getId();

				teamMemberRepository.save(TeamMember.create(team, member));
			}

			@Test
			@DisplayName("TeamDetail 반환한다")
			void success() {

				TeamUpdateRequest teamUpdateRequest = new TeamUpdateRequest(
					id,
					"newTestTitle",
					10L,
					12
				);

				TeamDetails result = teamService.update(
					member.getUuid(),
					teamUpdateRequest.teamId(),
					teamUpdateRequest.newTitle(),
					teamUpdateRequest.notificationDay(),
					teamUpdateRequest.notificationHour()
				);

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
					() -> teamService.update(member.getUuid(), 999L, "test", 1L, 1L)
				).isInstanceOf(TeamNotFoundException.class);
			}
		}
	}


	@Nested
	@DisplayName("delete 메서드는")
	class Describe_delete {

		@BeforeEach
		void setUp() {
			Team team = teamRepository.save(
				Team.create("test", inviteGenerator.createInviteCode()));

			id = team.getId();
			teamMemberRepository.save(TeamMember.create(team, member));
		}

		@Nested
		@DisplayName("올바른 요청을 받았을 때")
		class Delete_success {

			@Test
			@DisplayName("deleted를 true로 바꾼다.")
			void success() {

				teamService.delete(
					member.getUuid(),
					id
				);

				assertThat(teamRepository.findById(id)).isNotNull();
			}
		}


		@Nested
		@DisplayName("팀에 속하지 않은 멤버가 요청했을 때")
		class Delete_InvalidMember_Expect_InvalidMemberException {

			Member member = MockUser.createMockMember("kakao 123456");

			@Test
			@DisplayName("InvalidMemberException 발생시킨다.")
			void fail() {

				memberRepository.save(member);

				assertThatThrownBy(
					() -> teamService.delete(member.getUuid(), id)
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
					() -> teamService.delete(member.getUuid(), 999L)
				).isInstanceOf(TeamNotFoundException.class);
			}
		}
	}


	@Nested
	@DisplayName("Invite 메서드는")
	class Describe_invite {

		@Nested
		@DisplayName("유효한 초대코드 일 때")
		class Invite_ValidInviteCode_Expect_Success {

			String inviteCode = inviteGenerator.createInviteCode();

			@BeforeEach
			void setUp() {
				teamRepository.save(Team.create("test", inviteCode));
			}

			@Test
			void success() {

				assertThatCode(
					() -> teamService.invite(member.getUuid(), inviteCode)).doesNotThrowAnyException();

			}
		}
	}

}