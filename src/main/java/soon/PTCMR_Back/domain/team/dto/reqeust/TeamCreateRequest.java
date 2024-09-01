package soon.PTCMR_Back.domain.team.dto.reqeust;

import jakarta.validation.constraints.NotBlank;

public record TeamCreateRequest(
	@NotBlank(message = "팀명은 필수입니다.")
    String title
) {

}
