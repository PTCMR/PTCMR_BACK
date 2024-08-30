package soon.PTCMR_Back.domain.team.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import soon.PTCMR_Back.domain.member.MockUser;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.member.repository.MemberRepository;
import soon.PTCMR_Back.domain.team.data.TeamData;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamCreateRequest;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamInviteRequest;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamUpdateRequest;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.entity.TeamMember;
import soon.PTCMR_Back.domain.team.repository.TeamMemberRepository;
import soon.PTCMR_Back.domain.team.repository.TeamRepository;

@AutoConfigureMockMvc(addFilters = false)
@Transactional
@SpringBootTest
public class TeamControllerTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private TeamMemberRepository teamMemberRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	Member member;
	Team team;

	@BeforeEach
	void setUp() {
		MockUser.createMockUser();
		member = MockUser.createMockMember("kakao 1234");
		team = TeamData.createTeam();

		teamRepository.save(team);
		memberRepository.save(member);
		teamMemberRepository.save(TeamMember.create(team, member));
	}

	@Test
	@DisplayName("[POST] /api/v1/team 요청 시 팀 생성")
	void create() throws Exception {
		TeamCreateRequest teamCreateRequest = new TeamCreateRequest(TeamData.TEAM_TITLE);

		String json = getJson(teamCreateRequest);

		mockMvc.perform(post("/api/v1/team")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isCreated())
			.andDo(print());

	}

	@Test
	@DisplayName("[PUT] api/v1/team 요청 시 팀 업데이트")
	void update() throws Exception {
		TeamUpdateRequest teamUpdateRequest = new TeamUpdateRequest(team.getId(), "TestUpdate", 5L, 12L);
		String json = getJson(teamUpdateRequest);

		mockMvc.perform(put("/api/v1/team")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("[DELETE] /api/v1/team/{teamId} 요청 시 팀 삭제")
	void delete() throws Exception {

		mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/team/{teamId}", team.getId()))
			.andExpect(status().isNoContent())
			.andDo(print());
	}

	@Test
	@DisplayName("[POST] /api/v1/team/invite 요청 시 팀 생성")
	void invite() throws Exception {

		teamMemberRepository.deleteAll();
		String json = getJson(new TeamInviteRequest(team.getInviteCode()));

		mockMvc.perform(post("/api/v1/team/invite")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isNoContent())
			.andDo(print());

	}

	private String getJson(Object inviteCode) throws JsonProcessingException {
		return objectMapper.writeValueAsString(inviteCode);
	}
}
