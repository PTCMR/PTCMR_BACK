package soon.PTCMR_Back.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static soon.PTCMR_Back.domain.product.entity.ProductTest.createProduct;
import static soon.PTCMR_Back.domain.product.entity.ProductTest.pagingSetUp;
import static soon.PTCMR_Back.domain.product.repository.ProductPaginationRepository.PAGE_SIZE;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soon.PTCMR_Back.domain.category.entity.Category;
import soon.PTCMR_Back.domain.category.repository.CategoryJpaRepository;
import soon.PTCMR_Back.domain.product.dto.ProductPaginationDto;
import soon.PTCMR_Back.domain.product.dto.request.ProductCreateRequest;
import soon.PTCMR_Back.domain.product.dto.request.ProductPaginationRequest;
import soon.PTCMR_Back.domain.product.dto.request.ProductUpdateRequest;
import soon.PTCMR_Back.domain.product.dto.response.ProductDetailResponse;
import soon.PTCMR_Back.domain.product.dto.response.ProductPaginationResponseWrapper;
import soon.PTCMR_Back.domain.product.entity.Product;
import soon.PTCMR_Back.domain.product.entity.ProductStatus;
import soon.PTCMR_Back.domain.product.entity.StorageType;
import soon.PTCMR_Back.domain.product.repository.ProductJpaRepository;
import soon.PTCMR_Back.domain.product.repository.ProductPaginationRepository;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.repository.TeamJpaRepository;
import soon.PTCMR_Back.global.util.invite.InviteCodeGenerator;


@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductJpaRepository productJpaRepository;

    @Autowired
    ProductService productService;

    @Autowired
    private TeamJpaRepository teamJpaRepository;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    private Team team;

    private Category category;


    @BeforeEach
    void clean() {
        productJpaRepository.deleteAll();
        categoryJpaRepository.deleteAll();
        teamJpaRepository.deleteAll();

        InviteCodeGenerator inviteCodeGenerator = new InviteCodeGenerator();
        team = teamJpaRepository.save(Team.create("title", inviteCodeGenerator.createInviteCode()));
        category = categoryJpaRepository.save(Category.create("testCategory", team));
    }

    @Test
    @DisplayName("상품 자체 등록")
    void productSelfCreate() {
        // given;
        ProductCreateRequest request = new ProductCreateRequest("자일리톨",
            LocalDateTime.now().plusDays(19), 4, "", StorageType.FROZEN.toString(),
            true, "설명", team.getId(), category.getId());

        // when
        productService.create(request);

        // then
        assertThat(productJpaRepository.count()).isEqualTo(1);

        Product product = productJpaRepository.findAll().getFirst();
        assertEquals(request.name(), product.getName());
        assertEquals(request.quantity(), product.getQuantity());
        assertEquals(ProductStatus.YELLOW, product.getStatus());
    }

    @Test
    @DisplayName("상품 삭제")
    void delete() {
        Product product = createProduct(team, category);
        productJpaRepository.save(product);

        productService.delete(product.getId());

        assertThat(productJpaRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("상품 수정")
    void update() {
        // given
        Product product = createProduct(team, category);
        productJpaRepository.saveAndFlush(product);

        String newName = "자일리톨이 아니라 후라보노";
        int newQuantity = 32;

        ProductUpdateRequest request = new ProductUpdateRequest(newName,
            product.getExpirationDate(), newQuantity, product.getImageUrl(),
            product.getStorageType().toString(), product.isRepurchase(), product.getDescription(),
            category.getTitle());

        // when
        productService.update(product.getId(), request);

        Product updatedProduct = productJpaRepository.findById(product.getId()).get();

        // then
        assertThat(newName).isEqualTo(updatedProduct.getName());
        assertThat(newQuantity).isEqualTo(updatedProduct.getQuantity());
    }

    @Test
    @DisplayName("상품 단건 조회")
    void detailProduct() {
        // given
        Product product = createProduct(team, category);
        productJpaRepository.save(product);

        // when
        ProductDetailResponse detail = productService.detail(product.getId());

        // then
        assertThat(detail.name()).isEqualTo(product.getName());
        assertThat(detail.quantity()).isEqualTo(product.getQuantity());
        assertThat(detail.imageUrl()).isEqualTo(product.getImageUrl());
        assertThat(StorageType.valueOf(detail.storageType())).isEqualTo(product.getStorageType());
        assertThat(detail.repurchase()).isEqualTo(product.isRepurchase());
        assertThat(detail.description()).isEqualTo(product.getDescription());
        assertThat(ProductStatus.valueOf(detail.status())).isEqualTo(product.getStatus());
        assertThat(detail.expirationDate()).isEqualTo(product.getExpirationDate());
    }

    @Test
    @DisplayName("상품 페이징 - 첫 페이지")
    void productPaginationFirstPage() {
        // given
        List<Product> pagingSetUp = pagingSetUp(team, category);
        productJpaRepository.saveAll(pagingSetUp);

        Long lastProductId = null;
        String sortOption = "CREATE_DATE_DESC";
        String category = "";

        ProductPaginationRequest request = new ProductPaginationRequest(lastProductId, sortOption,
            category, team.getId());

        // when
        ProductPaginationResponseWrapper paginatedProducts = productService.getPaginatedProducts(
            request);
        List<ProductPaginationDto> products = paginatedProducts.products();
        boolean hasNext = paginatedProducts.hasNext();

        // then
        assertThat(paginatedProducts).isNotNull();
        assertThat(hasNext).isTrue();
        assertThat(products.size()).isEqualTo(PAGE_SIZE);
    }

    @Test
    @DisplayName("상품 페이징 - 마지막 페이지")
    void productPaginationLastPage() {
        // given
        List<Product> pagingSetUp = pagingSetUp(team, category);
        productJpaRepository.saveAll(pagingSetUp);

        Long lastProductId = pagingSetUp.get(PAGE_SIZE - 1).getId();
        String sortOption = "CREATE_DATE_DESC";
        String category = "";

        ProductPaginationRequest request = new ProductPaginationRequest(lastProductId, sortOption,
            category, team.getId());

        // when
        ProductPaginationResponseWrapper paginatedProducts = productService.getPaginatedProducts(
            request);
        List<ProductPaginationDto> products = paginatedProducts.products();
        boolean hasNext = paginatedProducts.hasNext();

        // then
        assertThat(paginatedProducts).isNotNull();
        assertThat(hasNext).isFalse();
        assertThat(products.size()).isEqualTo(ProductPaginationRepository.PAGE_SIZE);
    }
}