package soon.PTCMR_Back.domain.category.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.PTCMR_Back.domain.category.entity.Category;
import soon.PTCMR_Back.global.exception.CategoryNotFoundException;

@RequiredArgsConstructor
@Repository
public class CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    public boolean existCategoryTitle(String categoryTitle) {
        return categoryJpaRepository.existsByTitle(categoryTitle);
    }

    public Long save(Category category) {
        return categoryJpaRepository.save(category).getId();
    }

    public Category findById(Long id) {
        return categoryJpaRepository.findById(id).orElseThrow(
            CategoryNotFoundException::new
        );
    }
}
