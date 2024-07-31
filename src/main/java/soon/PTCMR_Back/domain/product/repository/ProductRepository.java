package soon.PTCMR_Back.domain.product.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.PTCMR_Back.domain.product.entity.Product;

@RequiredArgsConstructor
@Repository
public class ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public void save(Product product) {
        productJpaRepository.save(product);
    }
}
