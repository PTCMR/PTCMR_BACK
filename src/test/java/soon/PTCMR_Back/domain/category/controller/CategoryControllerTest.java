package soon.PTCMR_Back.domain.category.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soon.PTCMR_Back.domain.product.entity.ProductTest.createProduct;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    ProductJpaRepository productJpaRepository;

    @Autowired
    TeamJpaRepository teamJpaRepository;

    @Autowired
    InviteCodeGenerator codeGenerator;

    @Autowired
    TeamService teamService;

    @BeforeEach
    void setUp() {
//        categoryJpaRepository.deleteAll();
//        productJpaRepository.deleteAll();
//        teamJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("[POST] api/v1/category 요청 시 카테고리 생성")
    void create() throws Exception {
        // given
        Team team = TeamData.createTeam(codeGenerator.createInviteCode());
        teamJpaRepository.save(team);

        CategoryCreateRequest request = new CategoryCreateRequest("testTitle", team.getId());

        String json = objectMapper.writeValueAsString(request);

        // expected
        MvcResult result = mockMvc.perform(post("/api/v1/category")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$").isNumber())
            .andDo(print())
            .andReturn();

        Long categoryId = Long.parseLong(result.getResponse().getContentAsString());
        assertTrue(categoryJpaRepository.findById(categoryId).isPresent());
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
                .content(json)
            )
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @Test
    @DisplayName("[DELETE] api/v1/category 요청 시 카테고리 삭제")
    void deleteCategoryAndReassignProducts() throws Exception {
        // given
        Long teamId = teamService.create(String.valueOf(UUID.randomUUID()), "testTeamTitle");
        Team team = teamJpaRepository.findById(teamId).get();

        Category category = Category.create("testTitle", team);
        categoryJpaRepository.save(category);

        Product product = createProduct(team, category);
        productJpaRepository.save(product);

        CategoryDeleteRequest request = new CategoryDeleteRequest(category.getId(), team.getId());

        // expected
        mockMvc.perform(delete("/api/v1/category")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isNoContent())
            .andDo(print());
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
            .andDo(print());
    }
}