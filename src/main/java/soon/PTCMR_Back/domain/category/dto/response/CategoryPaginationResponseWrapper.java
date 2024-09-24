package soon.PTCMR_Back.domain.category.dto.response;

import java.util.List;
import lombok.Builder;
import soon.PTCMR_Back.domain.category.dto.CategoryPaginationDto;

@Builder
public record CategoryPaginationResponseWrapper(

    List<CategoryPaginationDto> categories,

    boolean hasNext
) {
}
