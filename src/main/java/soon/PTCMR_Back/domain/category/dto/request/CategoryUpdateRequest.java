package soon.PTCMR_Back.domain.category.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record CategoryUpdateRequest(

    @NotEmpty(message = "카테고리명 입력하세요.")
    String title
) {

}
