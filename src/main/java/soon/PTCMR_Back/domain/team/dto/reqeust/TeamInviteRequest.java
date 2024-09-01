package soon.PTCMR_Back.domain.team.dto.reqeust;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TeamInviteRequest(
	@NotBlank(message = "초대 코드는 필수입니다.")
	@Size(message = "초대 코드는 최대 8글자입니다." , max = 8)
	String inviteCode
) {

}
