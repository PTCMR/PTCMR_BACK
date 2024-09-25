package soon.PTCMR_Back.domain.category.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soon.PTCMR_Back.domain.product.entity.ProductTest.createProduct;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import soon.PTCMR_Back.domain.category.dto.request.CategoryCreateRequest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryDeleteRequest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryPaginationRequest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryUpdateRequest;
import soon.PTCMR_Back.domain.category.entity.Category;
import soon.PTCMR_Back.domain.category.repository.CategoryJpaRepository;
import soon.PTCMR_Back.domain.product.entity.Product;
import soon.PTCMR_Back.domain.product.repository.ProductJpaRepository;
import soon.PTCMR_Back.domain.team.data.TeamData;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.repository.TeamJpaRepository;
import soon.PTCMR_Back.domain.team.service.TeamService;
import soon.PTCMR_Back.global.util.invite.InviteCodeGenerator;

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dns-name.com", uriPort = 443)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
public class CategoryControllerDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private InviteCodeGenerator codeGenerator;

    @Autowired
    private TeamJpaRepository teamJpaRepository;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Autowired
    private TeamService teamService;

    @Test
    @DisplayName("[POST] api/v1/category 요청 시 카테고리 생성")
    void create() throws Exception {
        // given
        Team team = TeamData.createTeam(codeGenerator.createInviteCode());
        teamJpaRepository.save(team);

        CategoryCreateRequest request = new CategoryCreateRequest("testTitle", team.getId());

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/api/v1/category")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("category-create",
                requestFields(
                    fieldWithPath("title").description("카테고리 제목"),
                    fieldWithPath("teamId").description("팀 아이디")
                ))
            );
    }

    @Test
    @DisplayName("[PATCH] api/v1/category/{categoryId} 요청 시 카테고리 수정")
    void update() throws Exception {
        // given
        Team team = TeamData.createTeam(codeGenerator.createInviteCode());
        teamJpaRepository.save(team);

        Category category = Category.create("testTitle", team);
        categoryJpaRepository.save(category);

        Product product = createProduct(team, category);
        productJpaRepository.save(product);

        CategoryUpdateRequest request = new CategoryUpdateRequest("new Title", team.getId());
        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(patch("/api/v1/category/{categoryId}", category.getId())
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(document("category-update",
                pathParameters(parameterWithName("categoryId").description("카테고리 아이디")),
                requestFields(
                    fieldWithPath("title").description("카테고리 제목"),
                    fieldWithPath("teamId").description("팀 아이디")
                )
            ));
    }

    @Test
    @DisplayName("[DELETE] api/v1/category 요청 시 카테고리 삭제")
    void deleteCategory() throws Exception {
        // given
        Long teamId = teamService.create(String.valueOf(UUID.randomUUID()), "testTeamTitle");
        Team team = teamJpaRepository.findById(teamId).get();

        Category category = Category.create("testTitle", team);
        categoryJpaRepository.save(category);

        Product product = createProduct(team, category);
        productJpaRepository.save(product);

        CategoryDeleteRequest request = new CategoryDeleteRequest(category.getId(), team.getId());

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(delete("/api/v1/category")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(document("category-delete",
                requestFields(
                    fieldWithPath("categoryId").description("카테고리 아이디"),
                    fieldWithPath("teamId").description("팀 아이디")
                )
            ));
    }

    @Test
    @DisplayName("[GET] api/v1/category 요청 시 카테고리 페이징")
    void categoryPaginationList() throws Exception {
        // given
        Long teamId = teamService.create(String.valueOf(UUID.randomUUID()), "testTeamTitle");
        Team team = teamJpaRepository.findById(teamId).get();

        for (int i = 0; i < 5; i++) {
            Category category = Category.create("title" + i, team);
            categoryJpaRepository.save(category);
        }

        CategoryPaginationRequest request = new CategoryPaginationRequest(null, teamId);

        // expected
        mockMvc.perform(get("/api/v1/category")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("category-pagination",
                requestFields(
                    fieldWithPath("lastCategoryId").description("마지막으로 전달 받은 카테고리 아이디"),
                    fieldWithPath("teamId").description("팀 아이디")
                )
            ));
    }
}
