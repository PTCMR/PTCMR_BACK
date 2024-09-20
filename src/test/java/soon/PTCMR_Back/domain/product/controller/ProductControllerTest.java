package soon.PTCMR_Back.domain.product.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soon.PTCMR_Back.domain.product.entity.ProductTest.createProduct;
import static soon.PTCMR_Back.domain.product.entity.ProductTest.pagingSetUp;
import static soon.PTCMR_Back.domain.product.repository.ProductPaginationRepository.PAGE_SIZE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import soon.PTCMR_Back.domain.category.entity.Category;
import soon.PTCMR_Back.domain.category.repository.CategoryJpaRepository;
import soon.PTCMR_Back.domain.product.dto.request.ProductCreateRequest;
import soon.PTCMR_Back.domain.product.dto.request.ProductPaginationRequest;
import soon.PTCMR_Back.domain.product.dto.request.ProductUpdateRequest;
import soon.PTCMR_Back.domain.product.entity.Product;
import soon.PTCMR_Back.domain.product.repository.ProductJpaRepository;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.repository.TeamJpaRepository;
import soon.PTCMR_Back.global.util.invite.InviteCodeGenerator;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private TeamJpaRepository teamJpaRepository;

    private Team team;

    private Category category;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @BeforeEach
    void clean() {
        productJpaRepository.deleteAll();
        teamJpaRepository.deleteAll();
        categoryJpaRepository.deleteAll();

        InviteCodeGenerator inviteCodeGenerator = new InviteCodeGenerator();
        team = teamJpaRepository.save(Team.create("title", inviteCodeGenerator.createInviteCode()));
        category = categoryJpaRepository.save(Category.create("title", team));
    }

    @Test
    @DisplayName("[POST] /product 요청 시 상품 생성")
    void create() throws Exception {
        // given
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(1);

        ProductCreateRequest request = new ProductCreateRequest("자일리톨",
            expirationDate, 1, "", "Frozen",
            true, "이것은 자일리톨 껌이요", team.getId(), category.getId());

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/api/v1/product")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @Test
    @DisplayName("[DELETE] /product 요청 시 상품 삭제")
    void productDelete() throws Exception {
        // given
        Product product = createProduct(team, category);
        productJpaRepository.save(product);

        // expected
        mockMvc.perform(delete("/api/v1/product/{productId}", product.getId())
                .contentType(APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @Test
    @DisplayName("[PATCH] /product 요청 시 상품 수정")
    void update() throws Exception {
        // given
        Product product = createProduct(team, category);
        productJpaRepository.save(product);

        String newName = "후라보노";
        int newQuantity = 32;

        ProductUpdateRequest request = new ProductUpdateRequest(newName,
            product.getExpirationDate(), newQuantity,
            product.getImageUrl(), product.getStorageType().toString(),
            product.isRepurchase(), product.getDescription(), category.getTitle());

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(patch("/api/v1/product/{productId}", product.getId())
                .contentType(APPLICATION_JSON)
                .content(json)
            )
            .andExpect(status().isFound())
            .andDo(print());
    }

    @Test
    @DisplayName("[GET] /product/{productId}} 요청 시 상품 단건 조회")
    void detail() throws Exception {
        // given
        Product product = createProduct(team, category);
        productJpaRepository.save(product);

        // expected
        mockMvc.perform(get("/api/v1/product/{productId}", product.getId())
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.expirationDate").value(product.getExpirationDate().toString()))
            .andExpect(jsonPath("$.quantity").value(product.getQuantity()))
            .andExpect(jsonPath("$.imageUrl").value(product.getImageUrl()))
            .andExpect(jsonPath("$.status").value(product.getStatus().toString()))
            .andExpect(jsonPath("$.storageType").value(product.getStorageType().toString()))
            .andExpect(jsonPath("$.repurchase").value(product.isRepurchase()))
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andDo(print());
    }

    @Test
    @DisplayName("[GET] /product 요청 시 상품 페이징 조회")
    void getPaginatedProducts() throws Exception {
        // given
        List<Product> pagingSetUp = pagingSetUp(team, category);
        productJpaRepository.saveAll(pagingSetUp);

        Long lastProductId = null;
        String sortOption = "CREATE_DATE_DESC";
        String categoryTitle = null;

        ProductPaginationRequest request = new ProductPaginationRequest(
            lastProductId, sortOption, categoryTitle, team.getId());

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(get("/api/v1/product")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.products").isArray())
            .andExpect(jsonPath("$.products.length()").value(PAGE_SIZE))
            .andExpect(jsonPath("$.hasNext").value(true))
            .andDo(print());
    }
}