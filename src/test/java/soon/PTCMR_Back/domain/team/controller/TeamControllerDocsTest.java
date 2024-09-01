package soon.PTCMR_Back.domain.team.controller;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
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

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dns-name.com", uriPort = 443)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(RestDocumentationExtension.class)
@Transactional
@SpringBootTest
public class TeamControllerDocsTest {

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
		TeamCreateRequest teamCreateRequest = new TeamCreateRequest("test");

		String json = objectMapper.writeValueAsString(teamCreateRequest);

		mockMvc.perform(post("/api/v1/team")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isCreated())
			.andDo(print())
			.andDo(document("team-create",
				requestFields(
					fieldWithPath("title").description("팀 이름")
				)));

	}

	@Test
	@DisplayName("[POST] /api/v1/team 요청 시 팀 업데이트")
	void update() throws Exception {
		TeamUpdateRequest teamUpdateRequest = new TeamUpdateRequest(team.getId(), "TestUpdate", 5L, 12L);
		String json = objectMapper.writeValueAsString(teamUpdateRequest);

		mockMvc.perform(put("/api/v1/team")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("team-update",
				requestFields(
					fieldWithPath("teamId").description("팀 아이디"),
					fieldWithPath("newTitle").description("변경할 타이틀"),
					fieldWithPath("notificationDay").description("알림일"),
					fieldWithPath("notificationHour").description("알림날짜")
				)));

	}

	@Test
	@DisplayName("[DELETE] /api/v1/team/{teamId} 요청 시 팀 삭제")
	void delete() throws Exception {

		mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/team/{teamId}", team.getId()))
			.andExpect(status().isNoContent())
			.andDo(print())
			.andDo(document("team-delete",
				pathParameters(
					parameterWithName("teamId").description("팀 아이디")
				)
			));
	}

	@Test
	@DisplayName("[POST] /api/v1/team/invite 요청 시 팀 생성")
	void invite() throws Exception {

		teamMemberRepository.deleteAll();
		String json = objectMapper.writeValueAsString(new TeamInviteRequest(TeamData.INVITE_CODE));

		mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/team/invite")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isNoContent())
			.andDo(print())
			.andDo(document("team-invite",
				requestFields(
					fieldWithPath("inviteCode").description("초대 코드")
				)));

	}

}
