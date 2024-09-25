package soon.PTCMR_Back.domain.category.service;

import static soon.PTCMR_Back.domain.category.repository.CategoryPaginationRepository.PAGE_SIZE;
import static soon.PTCMR_Back.domain.category.repository.CategoryRepository.DEFAULT_CATEGORY_TITLE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.PTCMR_Back.domain.category.dto.CategoryPaginationDto;
import soon.PTCMR_Back.domain.category.dto.request.CategoryCreateRequest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryDeleteRequest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryPaginationRequest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryUpdateRequest;
import soon.PTCMR_Back.domain.category.dto.response.CategoryPaginationResponseWrapper;
import soon.PTCMR_Back.domain.category.entity.Category;
import soon.PTCMR_Back.domain.category.repository.CategoryPaginationRepository;
import soon.PTCMR_Back.domain.category.repository.CategoryRepository;
import soon.PTCMR_Back.domain.product.repository.ProductRepository;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.repository.TeamRepository;
import soon.PTCMR_Back.global.exception.CategoryExistException;

@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final TeamRepository teamRepository;
    private final ProductRepository productRepository;
    private final CategoryPaginationRepository categoryPaginationRepository;

    @Transactional
    public Long create(CategoryCreateRequest request) {
        validateCategoryExistsForTeam(request.title(), request.teamId());

        Team team = teamRepository.findById(request.teamId());
        Category category = Category.create(request.title(), team);
        categoryRepository.save(category);

        return category.getId();
    }

    @Transactional
    public void update(CategoryUpdateRequest request, Long categoryId) {
        validateCategoryExistsForTeam(request.title(), request.teamId());

        Category category = categoryRepository.findByIdAndTeamId(categoryId, request.teamId());
        category.update(request.title());
    }

    @Transactional
    public void createDefaultCategoryForTeam(Long teamId) {
        Team team = teamRepository.findById(teamId);
        Category category = Category.create(DEFAULT_CATEGORY_TITLE, team);

        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategoryAndReassignProducts(CategoryDeleteRequest request) {
        Category defaultCategory = categoryRepository
            .findByDefaultCategoryWithTeamId(request.teamId());
        Category categoryToDelete = categoryRepository
            .findByIdAndTeamId(request.categoryId(), request.teamId());

        reassignProductsToDefaultCategory(categoryToDelete.getId(), defaultCategory.getId());

        categoryRepository.delete(categoryToDelete);
    }

    @Transactional
    protected void reassignProductsToDefaultCategory(Long categoryId, Long defaultCategoryId) {
        productRepository.updateCategoryForProducts(categoryId, defaultCategoryId);
    }

    @Transactional(readOnly = true)
    protected void validateCategoryExistsForTeam(String title, Long teamId) {
        if (categoryRepository.existsByTitleAndTeamId(title, teamId)) {
            throw new CategoryExistException();
        }
    }

    @Transactional(readOnly = true)
    public CategoryPaginationResponseWrapper getPaginatedCategories(
        CategoryPaginationRequest request) {
        Team team = teamRepository.findById(request.teamId());

        List<CategoryPaginationDto> paginatedCategories = categoryPaginationRepository.getCategoryList(
            request.lastCategoryId(), team);

        boolean hasNext = determineHasNextPage(paginatedCategories);

        return CategoryPaginationResponseWrapper.builder()
            .categories(paginatedCategories)
            .hasNext(hasNext)
            .build();
    }

    private boolean determineHasNextPage(List<CategoryPaginationDto> paginatedList) {
        if (paginatedList.size() > PAGE_SIZE) {
            paginatedList.remove(PAGE_SIZE);
            return true;
        }
        return false;
    }
}
