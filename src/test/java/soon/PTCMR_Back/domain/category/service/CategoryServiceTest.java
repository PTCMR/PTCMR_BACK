package soon.PTCMR_Back.domain.category.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static soon.PTCMR_Back.domain.category.repository.CategoryPaginationRepository.PAGE_SIZE;
import static soon.PTCMR_Back.domain.product.entity.ProductTest.createProduct;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soon.PTCMR_Back.domain.category.dto.CategoryPaginationDto;
import soon.PTCMR_Back.domain.category.dto.request.CategoryCreateRequest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryDeleteRequest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryPaginationRequest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryUpdateRequest;
import soon.PTCMR_Back.domain.category.dto.response.CategoryPaginationResponseWrapper;
import soon.PTCMR_Back.domain.category.entity.Category;
import soon.PTCMR_Back.domain.category.repository.CategoryJpaRepository;
import soon.PTCMR_Back.domain.product.entity.Product;
import soon.PTCMR_Back.domain.product.repository.ProductJpaRepository;
import soon.PTCMR_Back.domain.team.data.TeamData;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.repository.TeamJpaRepository;
import soon.PTCMR_Back.domain.team.service.TeamService;
import soon.PTCMR_Back.global.exception.CannotModifyDefaultCategoryException;
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

    @Autowired
    private TeamService teamService;

    @BeforeEach
    void setUp() {
//        categoryJpaRepository.deleteAll();
//        productJpaRepository.deleteAll();
//        teamJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("카테고리 등록")
    void create() {
        // given
        Team team = TeamData.createTeam(codeGenerator.createInviteCode());
        teamJpaRepository.save(team);

        CategoryCreateRequest request = new CategoryCreateRequest("testTitle", team.getId());
        // when
        Long categoryId = categoryService.create(request);

        // then
        Category category = categoryJpaRepository.findById(categoryId).get();

        assertThat(category).isNotNull();
        assertThat(category.getTitle()).isEqualTo("testTitle");
        assertThat(category.getTeam().getId()).isEqualTo(team.getId());
    }

    @Test
    @DisplayName("카테고리 수정")
    void update() {
        // given
        Team team = TeamData.createTeam(codeGenerator.createInviteCode());
        teamJpaRepository.save(team);

        Category category = Category.create("testTitle", team);
        categoryJpaRepository.save(category);

        CategoryUpdateRequest request = new CategoryUpdateRequest("new Title", team.getId());

        // when
        categoryService.update(request, category.getId());

        // then
        Category updatedCategory = categoryJpaRepository.findById(category.getId()).get();
        assertThat(updatedCategory.getTitle()).isEqualTo(request.title());
    }

    @Test
    @DisplayName("기본 카테고리 수정 시 실패")
    void defaultCategoryUpdate() {
        // given
        Long teamId = teamService.create(String.valueOf(UUID.randomUUID()), "testTeamTitle");
        Category category = categoryJpaRepository.findByTitleAndTeamId("기본", teamId).get();

        CategoryUpdateRequest request = new CategoryUpdateRequest("new category title", teamId);

        // then
        assertThrows(CannotModifyDefaultCategoryException.class,
            () -> categoryService.update(request, category.getId())
        );
    }

    @Test
    @DisplayName("기본 카테고리 재할당")
    void reassignProductsToDefaultCategory() {
        // given
        Long teamId = teamService.create(String.valueOf(UUID.randomUUID()), "testTeamTitle");
        Team team = teamJpaRepository.findById(teamId).get();

        Category category = Category.create("testTitle", team);
        Category defaultCategory = categoryJpaRepository.findByTitleAndTeamId("기본", teamId).get();
        categoryJpaRepository.save(category);

        Product product = createProduct(team, category);
        productJpaRepository.saveAndFlush(product);

        // when
        categoryService.reassignProductsToDefaultCategory(category.getId(),
            defaultCategory.getId());

        // then
        Product updatedProduct = productJpaRepository.findById(product.getId()).get();
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getCategory().getId()).isEqualTo(defaultCategory.getId());
    }

    @Test
    @DisplayName("카테고리 삭제")
    void deleteCategoryAndReassignProducts() {
        // given
        Long teamId = teamService.create(String.valueOf(UUID.randomUUID()), "testTeamTitle");
        Team team = teamJpaRepository.findById(teamId).get();

        Category category = Category.create("testTitle", team);
        Category defaultCategory = categoryJpaRepository.findByTitleAndTeamId("기본", teamId).get();
        categoryJpaRepository.save(category);

        Product product = createProduct(team, category);
        productJpaRepository.save(product);

        CategoryDeleteRequest request = new CategoryDeleteRequest(category.getId(), team.getId());

        // when
        categoryService.deleteCategoryAndReassignProducts(request);

        // then
        Product updatedProduct = productJpaRepository.findById(product.getId()).get();
        assertThat(updatedProduct.getCategory().getId()).isEqualTo(defaultCategory.getId());
        assertThrows(NoSuchElementException.class,
            () -> categoryJpaRepository.findById(category.getId()).get()
        );
    }

    @Test
    @DisplayName("카테고리 페이징")
    void categoryPagination() {
        // given
        Long teamId = teamService.create(String.valueOf(UUID.randomUUID()), "testTeamTitle");
        Team team = teamJpaRepository.findById(teamId).get();

        for (int i = 0; i < 5; i++) {
            Category category = Category.create("title" + i, team);
            categoryJpaRepository.save(category);
        }

        CategoryPaginationRequest request = new CategoryPaginationRequest(null,
            team.getId());

        // when
        CategoryPaginationResponseWrapper response = categoryService.getPaginatedCategories(
            request);
        List<CategoryPaginationDto> categories = response.categories();
        boolean hasNext = response.hasNext();

        // then
        assertThat(categories).isNotNull();
        assertThat(categories.size()).isEqualTo(PAGE_SIZE);
        assertThat(hasNext).isTrue();
    }
}