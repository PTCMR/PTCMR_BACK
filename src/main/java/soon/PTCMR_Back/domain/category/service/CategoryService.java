package soon.PTCMR_Back.domain.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.PTCMR_Back.domain.category.dto.request.CategoryCreateRequest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryUpdateRequest;
import soon.PTCMR_Back.domain.category.entity.Category;
import soon.PTCMR_Back.domain.category.repository.CategoryRepository;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.repository.TeamRepository;
import soon.PTCMR_Back.global.exception.CategoryExistException;

@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public Long create(CategoryCreateRequest request) {
        validateCategoryTitle(request.title());
        boolean existedCategoryTitle = categoryRepository.existCategoryTitle(request.title());

        if (existedCategoryTitle) {
            throw new CategoryExistException();
        }

        Team team = teamRepository.findById(request.teamId());

        Category category = Category.create(request.title(), team);
        categoryRepository.save(category);

        return category.getId();
    }

    @Transactional
    public void update(CategoryUpdateRequest request, Long categoryId) {
        validateCategoryTitle(request.title());

        Category category = categoryRepository.findById(categoryId);
        category.update(request.title());

//        category.update(request.title(), product);
    }

    private void validateCategoryTitle(String title) {
        if (categoryRepository.existCategoryTitle(title)) {
            throw new CategoryExistException();
        }
    }

}
