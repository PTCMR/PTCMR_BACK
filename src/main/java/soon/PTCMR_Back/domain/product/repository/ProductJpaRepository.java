package soon.PTCMR_Back.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soon.PTCMR_Back.domain.product.entity.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

}
