package soon.PTCMR_Back.domain.product.dto;

import java.time.LocalDateTime;

public record ProductPaginationDto(

    Long productId,

    String name,

    LocalDateTime expirationDate,

    int quantity,

    String imageUrl,

    String status,

    String storageType,

    LocalDateTime createdDate
) {

}