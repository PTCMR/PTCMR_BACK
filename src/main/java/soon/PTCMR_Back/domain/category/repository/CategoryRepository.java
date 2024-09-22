package soon.PTCMR_Back.domain.category.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.PTCMR_Back.domain.category.entity.Category;
import soon.PTCMR_Back.global.exception.CategoryNotFoundException;

@RequiredArgsConstructor
@Repository
public class CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    public boolean existsByTitleAndTeamId(String categoryTitle, Long teamId) {
        return categoryJpaRepository.existsByTitleAndTeamId(categoryTitle, teamId);
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
