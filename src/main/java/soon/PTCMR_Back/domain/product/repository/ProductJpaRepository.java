package soon.PTCMR_Back.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import soon.PTCMR_Back.domain.product.entity.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p SET p.category.id = :defaultCategory WHERE p.category.id = :categoryId")
    void updateCategoryForProducts(@Param("categoryId") Long categoryId, @Param("defaultCategory") Long defaultCategoryId);

}
