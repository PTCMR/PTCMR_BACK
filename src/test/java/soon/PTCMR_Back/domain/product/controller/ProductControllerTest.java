package soon.PTCMR_Back.domain.product.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import soon.PTCMR_Back.domain.product.repository.ProductJpaRepository;

@AutoConfigureMockMvc
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
    @DisplayName("[POST] /post 요청 시 상품 생성")
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
            .andExpect(status().isOk())
            .andDo(print());
    }
}