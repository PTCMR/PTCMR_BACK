package soon.PTCMR_Back.domain.product.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
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
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import soon.PTCMR_Back.domain.product.dto.request.ProductCreateRequest;
import soon.PTCMR_Back.domain.product.dto.request.ProductUpdateRequest;
import soon.PTCMR_Back.domain.product.entity.Product;
import soon.PTCMR_Back.domain.product.repository.ProductJpaRepository;

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dns-name.com", uriPort = 443)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
public class ProductControllerDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @BeforeEach
    void clean() {
        productJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 셀프 등록")
    void createSelfProduct() throws Exception {
        // given
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(1);

        ProductCreateRequest request = new ProductCreateRequest("자일리톨", expirationDate,
            1, "", "FROZEN",
            true, "이것은 자일리톨 껌이요", 1L);

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/api/v1/product")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("product-create",
                requestFields(
                    fieldWithPath("name").description("제목"),
                    fieldWithPath("expirationDate").description("유통기간"),
                    fieldWithPath("quantity").description("수량"),
                    fieldWithPath("imageUrl").description("이미지 주소"),
                    fieldWithPath("storageType").description("보관 방법"),
                    fieldWithPath("repurchase").description("재구매 여부"),
                    fieldWithPath("description").description("설명"),
                    fieldWithPath("teamId").description("팀 번호")
                )
            ));
    }

    @Test
    @DisplayName("상품 삭제")
    void productDelete() throws Exception {
        // given
        Product product = createProduct();
        productJpaRepository.saveAndFlush(product);

        // expected
        mockMvc.perform(delete("/api/v1/product/{productId}", product.getId())
                .contentType(APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(document("product-delete",
                pathParameters(parameterWithName("productId").description("상품 ID"))));
    }

    @Test
    @DisplayName("상품 수정")
    void update() throws Exception {
        // given
        Product product = createProduct();
        productJpaRepository.saveAndFlush(product);

        String newName = "후라보노";
        int newQuantity = 32;

        ProductUpdateRequest request = new ProductUpdateRequest(newName,
            product.getExpirationDate(), newQuantity,
            product.getImageUrl(), product.getStorageType().toString(),
            product.isRepurchase(), product.getDescription());

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(patch("/api/v1/product/{productId}", product.getId())
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isFound())
            .andDo(print())
            .andDo(document("product-update",
                pathParameters(parameterWithName("productId").description("상품 ID")),
                requestFields(
                    fieldWithPath("name").description("상품 이름"),
                    fieldWithPath("expirationDate").description("유통기간"),
                    fieldWithPath("quantity").description("수량"),
                    fieldWithPath("imageUrl").description("이미지 주소"),
                    fieldWithPath("storageType").description("보관 방법"),
                    fieldWithPath("repurchase").description("재구매 여부"),
                    fieldWithPath("description").description("상품 설명")
                )
            ));
    }
}
