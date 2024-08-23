package soon.PTCMR_Back.domain.team.dto.response;

import lombok.Builder;
import soon.PTCMR_Back.domain.team.entity.Team;

@Builder
public record TeamDetails(
	Long teamId,
	String title,
	long notificationDay,
	long notificationHour
) {

	public static TeamDetails from(Team team) {
		return TeamDetails.builder()
			.teamId(team.getId())
			.title(team.getTitle())
			.notificationDay(team.getSchedule().getDay())
			.notificationHour(team.getSchedule().getHour())
			.build();
	}
}
