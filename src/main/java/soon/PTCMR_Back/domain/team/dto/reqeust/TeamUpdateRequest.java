package soon.PTCMR_Back.domain.team.dto.reqeust;

import jakarta.validation.constraints.NotBlank;

public record TeamUpdateRequest(
	@NotBlank(message = "팀 아이디는 필수입니다.")
	Long teamId,
	@NotBlank(message = "팀 이름은 필수입니다.")
	String newTitle,
	@NotBlank(message = "")
	long notificationDay,
	@NotBlank(message = "")
	long notificationHour
) {

}
