package soon.PTCMR_Back.domain.product.repository;

import java.util.List;
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

    public List<Product> findAllByCategory(Long categoryId) {
        return productJpaRepository.findAllByCategoryId(categoryId);
    }
}
