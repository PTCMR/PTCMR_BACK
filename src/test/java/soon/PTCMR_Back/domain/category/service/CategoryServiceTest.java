package soon.PTCMR_Back.domain.category.service;

import static soon.PTCMR_Back.domain.product.entity.ProductTest.createProduct;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryCreateRequest;
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
        Product product = createProduct();
        productJpaRepository.save(product);

        Team team = TeamData.createTeam(codeGenerator.createInviteCode());
        teamJpaRepository.save(team);

        CategoryCreateRequest request = new CategoryCreateRequest("testTitle", team.getId(),
            product.getId());
        // when
        Long categoryId = categoryService.create(request);

        // then
        Category category = categoryJpaRepository.findById(categoryId).get();

        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getTitle()).isEqualTo("testTitle");
        Assertions.assertThat(category.getTeam().getId()).isEqualTo(team.getId());
        Assertions.assertThat(category.getProduct().getId()).isEqualTo(product.getId());
    }
}