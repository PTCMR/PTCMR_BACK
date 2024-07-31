package soon.PTCMR_Back.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soon.PTCMR_Back.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
