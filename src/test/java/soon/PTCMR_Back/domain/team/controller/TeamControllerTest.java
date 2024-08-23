package soon.PTCMR_Back.domain.team.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import soon.PTCMR_Back.domain.member.MockUser;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamCreateRequest;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamUpdateRequest;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class TeamControllerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

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
			.andDo(print());

	}

	@Test
	@DisplayName("[PUT] api/v1/team 요청 시 팀 업데이트")
	void update() throws Exception {
		TeamUpdateRequest teamUpdateRequest = new TeamUpdateRequest(2L, "TestUpdate", 5L, 12L);
		String json = objectMapper.writeValueAsString(teamUpdateRequest);

		mockMvc.perform(put("/api/v1/team")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("[DELETE] /api/v1/team/{teamId} 요청 시 팀 삭제")
	void delete() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/team/2"))
			.andExpect(status().isNoContent())
			.andDo(print());
	}
}
