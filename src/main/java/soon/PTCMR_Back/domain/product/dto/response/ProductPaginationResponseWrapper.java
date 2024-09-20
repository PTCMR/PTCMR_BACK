package soon.PTCMR_Back.domain.product.dto.response;

import java.util.List;
import lombok.Builder;
import soon.PTCMR_Back.domain.product.dto.ProductPaginationDto;

@Builder
public record ProductPaginationResponseWrapper(

    List<ProductPaginationDto> products,

    boolean hasNext,

    String categoryTitle
) {

}
