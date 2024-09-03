package soon.PTCMR_Back.domain.category.service;

import static org.assertj.core.api.Assertions.assertThat;
import static soon.PTCMR_Back.domain.product.entity.ProductTest.createProduct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryCreateRequest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryUpdateRequest;
import soon.PTCMR_Back.domain.category.entity.Category;
import soon.PTCMR_Back.domain.category.repository.CategoryJpaRepository;
import soon.PTCMR_Back.domain.product.entity.Product;
import soon.PTCMR_Back.domain.product.repository.ProductJpaRepository;
import soon.PTCMR_Back.domain.team.data.TeamData;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.repository.TeamJpaRepository;
import soon.PTCMR_Back.global.util.invite.InviteCodeGenerator;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    ProductJpaRepository productJpaRepository;

    @Autowired
    TeamJpaRepository teamJpaRepository;

    @Autowired
    InviteCodeGenerator codeGenerator;

    @BeforeEach
    void setUp() {
        categoryJpaRepository.deleteAll();
        productJpaRepository.deleteAll();
        teamJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("카테고리 등록")
    void create() {
        // given
        Team team = TeamData.createTeam(codeGenerator.createInviteCode());
        teamJpaRepository.save(team);

        Product product = createProduct(team.getId());
        productJpaRepository.save(product);

        CategoryCreateRequest request = new CategoryCreateRequest("testTitle", team.getId(),
            product.getId());
        // when
        Long categoryId = categoryService.create(request);

        // then
        Category category = categoryJpaRepository.findById(categoryId).get();

        assertThat(category).isNotNull();
        assertThat(category.getTitle()).isEqualTo("testTitle");
        assertThat(category.getTeam().getId()).isEqualTo(team.getId());
        assertThat(category.getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    @DisplayName("카테고리 수정")
    void update() {
        // given
        Team team = TeamData.createTeam(codeGenerator.createInviteCode());
        teamJpaRepository.save(team);

        Product product = createProduct(team.getId());
        productJpaRepository.save(product);

        Category category = Category.create("testTitle", team, product);
        categoryJpaRepository.save(category);

        CategoryUpdateRequest request = new CategoryUpdateRequest("new Title", product.getId());

        // when
        categoryService.update(request, category.getId());

        // then
        Category updatedCategory = categoryJpaRepository.findById(category.getId()).get();
        assertThat(updatedCategory.getTitle()).isEqualTo(request.title());
        assertThat(updatedCategory.getProduct().getId()).isEqualTo(request.productId());
    }
}