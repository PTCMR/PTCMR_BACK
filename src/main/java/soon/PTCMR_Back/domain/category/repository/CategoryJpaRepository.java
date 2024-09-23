package soon.PTCMR_Back.domain.category.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import soon.PTCMR_Back.domain.category.entity.Category;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    boolean existsByTitleAndTeamId(String title, Long teamId);

    Optional<Category> findByIdAndTeamId(Long categoryId, Long teamId);

    Optional<Category> findByTitleAndTeamId(String title, Long teamId);
}
