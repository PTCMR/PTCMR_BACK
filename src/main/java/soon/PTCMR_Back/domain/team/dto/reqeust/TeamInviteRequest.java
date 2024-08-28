package soon.PTCMR_Back.domain.team.dto.reqeust;

import jakarta.validation.constraints.NotBlank;

public record TeamInviteRequest(
	@NotBlank(message = "초대 코드는 필수입니 다.")
	String inviteCode
) {

}
