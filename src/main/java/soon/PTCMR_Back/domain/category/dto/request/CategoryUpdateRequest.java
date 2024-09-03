package soon.PTCMR_Back.domain.category.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record CategoryUpdateRequest(

    @NotEmpty(message = "카테고리명 입력하세요.")
    String title,

    @Positive(message = "상품 번호를 제대로 입력하세요.")
    long productId
) {

}
