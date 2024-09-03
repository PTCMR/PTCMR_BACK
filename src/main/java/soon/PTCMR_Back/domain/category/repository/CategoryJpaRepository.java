package soon.PTCMR_Back.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soon.PTCMR_Back.domain.category.entity.Category;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    boolean existsByTitle(String title);
}
