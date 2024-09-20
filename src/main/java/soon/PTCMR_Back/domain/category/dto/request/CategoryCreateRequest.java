package soon.PTCMR_Back.domain.category.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record CategoryCreateRequest(

    @NotEmpty(message = "카테고리 이름을 입력해주세요.")
    String title,

    @Positive(message = "팀 아이디를 제대로 입력해주세요.")
    Long teamId
) {

}
