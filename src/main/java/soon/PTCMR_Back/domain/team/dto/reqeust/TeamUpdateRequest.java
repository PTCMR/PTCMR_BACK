package soon.PTCMR_Back.domain.team.dto.reqeust;

public record TeamUpdateRequest(
	Long teamId,
	String newTitle,
	long notificationDay,
	long notificationHour
) {

}
