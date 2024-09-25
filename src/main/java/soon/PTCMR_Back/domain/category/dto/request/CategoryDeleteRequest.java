package soon.PTCMR_Back.domain.category.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CategoryDeleteRequest(

    @NotNull(message = "카테고리 아이디를 입력하세요.")
    @Positive(message = "카테고리 아이디를 제대로 입력해주세요.")
    Long categoryId,

    @NotNull(message = "팀 아이디를 입력하세요.")
    @Positive(message = "팀 아이디를 제대로 입력해주세요.")
    Long teamId
) {
}
