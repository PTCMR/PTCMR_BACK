package soon.PTCMR_Back.domain.team.controller;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
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
import org.springframework.test.web.servlet.MockMvc;
import soon.PTCMR_Back.domain.member.MockUser;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamCreateRequest;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamUpdateRequest;

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dns-name.com", uriPort = 443)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
public class TeamControllerDocsTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		MockUser.createMockUser();
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
	@DisplayName("[POST] /api/v1/team 요청 시 팀 생성")
	void update() throws Exception {
		TeamUpdateRequest teamUpdateRequest = new TeamUpdateRequest(2L, "TestUpdate", 5L, 12L);
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

}
