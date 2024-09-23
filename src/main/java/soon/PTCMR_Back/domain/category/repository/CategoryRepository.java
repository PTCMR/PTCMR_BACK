package soon.PTCMR_Back.domain.category.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.PTCMR_Back.domain.category.entity.Category;
import soon.PTCMR_Back.global.exception.CategoryNotFoundException;

@RequiredArgsConstructor
@Repository
public class CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    public static final String DEFAULT_CATEGORY_TITLE = "기본";

    public boolean existsByTitleAndTeamId(String categoryTitle, Long teamId) {
        return categoryJpaRepository.existsByTitleAndTeamId(categoryTitle, teamId);
    }

    public Long save(Category category) {
        return categoryJpaRepository.save(category).getId();
    }

    public Category findByIdAndTeamId(Long categoryId, Long teamId) {
        return categoryJpaRepository.findByIdAndTeamId(categoryId, teamId)
            .orElseThrow(CategoryNotFoundException::new);
    }

    public Category findByDefaultCategoryWithTeamId(Long teamId) {
        return categoryJpaRepository.findByTitleAndTeamId(DEFAULT_CATEGORY_TITLE, teamId)
            .orElseThrow(CategoryNotFoundException::new);
    }

    public void delete(Category category) {
        categoryJpaRepository.delete(category);
    }
}
