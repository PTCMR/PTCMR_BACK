package soon.PTCMR_Back.domain.category.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CategoryUpdateRequest(

    @NotEmpty(message = "카테고리명 입력하세요.")
    String title,

    @NotNull(message = "팀 아이디를 입력하세요.")
    @Positive(message = "팀 아이디를 제대로 입력하세요.")
    Long teamId
) {

}
