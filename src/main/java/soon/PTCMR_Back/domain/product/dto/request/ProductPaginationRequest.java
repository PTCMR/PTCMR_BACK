package soon.PTCMR_Back.domain.product.dto.request;

import soon.PTCMR_Back.domain.product.entity.ProductSortOption;
import soon.PTCMR_Back.global.annotation.ValidEnum;

public record ProductPaginationRequest(

    Long lastProductId,

    @ValidEnum(message = "정확한 정렬 조건을 입력해주세요", verifyClass = ProductSortOption.class, ignoreCase = true)
    String sortOption,

    String categoryTitle,

    Long teamId
) {

}
