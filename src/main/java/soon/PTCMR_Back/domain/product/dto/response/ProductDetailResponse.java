package soon.PTCMR_Back.domain.product.dto.response;

import java.time.LocalDateTime;
import soon.PTCMR_Back.domain.product.entity.Product;

public record ProductDetailResponse(

    String name,

    LocalDateTime expirationDate,

    int quantity,

    String imageUrl,

    String status,

    String storageType,

    boolean repurchase,

    String description
) {

    public static ProductDetailResponse from(Product product) {
        return new ProductDetailResponse(
            product.getName(),
            product.getExpirationDate(),
            product.getQuantity(),
            product.getImageUrl(),
            String.valueOf(product.getStatus()),
            String.valueOf(product.getStorageType()),
            product.isRepurchase(),
            product.getDescription()
        );
    }
}
