package soon.PTCMR_Back.domain.product.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.PTCMR_Back.domain.product.entity.Product;
import soon.PTCMR_Back.global.exception.ProductNotFoundException;

@RequiredArgsConstructor
@Repository
public class ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public void save(Product product) {
        productJpaRepository.save(product);
    }

    public Product findById(Long id) {
        return productJpaRepository.findById(id)
            .orElseThrow(ProductNotFoundException::new);
    }

    public void updateCategoryForProducts(Long categoryId, Long defaultCategoryId) {
        productJpaRepository.updateCategoryForProducts(categoryId, defaultCategoryId);
    }
}
