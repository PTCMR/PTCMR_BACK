package soon.PTCMR_Back.domain.product.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import soon.PTCMR_Back.domain.category.entity.Category;
import soon.PTCMR_Back.domain.product.entity.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategoryId(Long categoryId);
}
