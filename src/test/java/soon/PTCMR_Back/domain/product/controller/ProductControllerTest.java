package soon.PTCMR_Back.domain.product.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static soon.PTCMR_Back.domain.product.entity.ProductTest.createProduct;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import soon.PTCMR_Back.domain.product.dto.request.ProductCreateRequest;
import soon.PTCMR_Back.domain.product.dto.request.ProductUpdateRequest;
import soon.PTCMR_Back.domain.product.entity.Product;
import soon.PTCMR_Back.domain.product.repository.ProductJpaRepository;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @BeforeEach
    void clean() {
        productJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("[POST] /product 요청 시 상품 생성")
    void create() throws Exception {
        // given
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(1);

        ProductCreateRequest request = new ProductCreateRequest("자일리톨",
            expirationDate, 1, "", "FROZEN",
            true, "이것은 자일리톨 껌이요", 1L);

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @Test
    @DisplayName("[DELETE] /product 요청 시 상품 삭제")
    void productDelete() throws Exception {
        // given
        Product product = createProduct();
        productJpaRepository.save(product);

        // expected
        mockMvc.perform(delete("/api/v1/product/{productId}", product.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @Test
    @DisplayName("[PATCH] /product 요청 시 상품 수정")
    void update() throws Exception {
        // given
        Product product = createProduct();
        productJpaRepository.save(product);

        String newName = "후라보노";
        int newQuantity = 32;

        ProductUpdateRequest request = new ProductUpdateRequest(newName,
            product.getExpirationDate(), newQuantity,
            product.getImageUrl(), product.getStorageType().toString(),
            product.isRepurchase(), product.getDescription());

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(patch("/api/v1/product/{productId}", product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
            )
            .andExpect(status().isFound())
            .andDo(print());
    }
}