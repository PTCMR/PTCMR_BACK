package soon.PTCMR_Back.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static soon.PTCMR_Back.domain.product.entity.ProductTest.createProduct;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soon.PTCMR_Back.domain.product.dto.request.ProductCreateRequest;
import soon.PTCMR_Back.domain.product.dto.request.ProductUpdateRequest;
import soon.PTCMR_Back.domain.product.entity.Product;
import soon.PTCMR_Back.domain.product.entity.ProductStatus;
import soon.PTCMR_Back.domain.product.entity.StorageType;
import soon.PTCMR_Back.domain.product.repository.ProductJpaRepository;


@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductJpaRepository productJpaRepository;

    @Autowired
    ProductService productService;

    @BeforeEach
    void clean() {
        productJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 자체 등록")
    void productSelfCreate() {
        // given
        ProductCreateRequest request = new ProductCreateRequest("자일리톨",
            LocalDateTime.now().plusDays(19), 2, "", StorageType.ROOM_TEMPERATURE.toString(),
            true, "이것은 자일리톨 껌이요", 1L);

        // when
        productService.create(request);

        // then
        assertThat(productJpaRepository.count()).isEqualTo(1);

        Product product = productJpaRepository.findAll().getFirst();
        Assertions.assertEquals(request.getName(), product.getName());
        Assertions.assertEquals(request.getQuantity(), product.getQuantity());

        Assertions.assertEquals(ProductStatus.YELLOW, product.getStatus());
    }

    @Test
    @DisplayName("상품 삭제")
    void delete() {
        Product product = createProduct();

        productService.delete(product.getId());

        assertThat(productJpaRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("상품 수정")
    void update() {
        // given
        Product product = createProduct();
        productJpaRepository.saveAndFlush(product);

        String newName = "자일리톨이 아니라 후라보노";
        int quantity = 32;

        ProductUpdateRequest request = new ProductUpdateRequest(newName,
            product.getExpirationDate(), quantity,
            product.getImageUrl(), product.getStorageType().toString(),
            product.isRepurchase(), product.getDescription());

        // when
        productService.update(product.getId(), request);

        Product updatedProduct = productJpaRepository.findById(product.getId()).get();

        // then
        assertThat(newName).isEqualTo(updatedProduct.getName());
        assertThat(quantity).isEqualTo(updatedProduct.getQuantity());
    }
}