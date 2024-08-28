package soon.PTCMR_Back.domain.team.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamCreateRequest;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamUpdateRequest;
import soon.PTCMR_Back.domain.team.dto.response.TeamDetails;
import soon.PTCMR_Back.domain.team.service.TeamService;
import soon.PTCMR_Back.global.oauth.CustomOAuth2User;

@RestController
@RequestMapping("/api/v1/team")
@RequiredArgsConstructor
public class TeamController {

	private final TeamService teamService;

	@PostMapping
	public ResponseEntity<Long> createTeam(
		@AuthenticationPrincipal final CustomOAuth2User user,
		@RequestBody final TeamCreateRequest teamCreateRequest
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(teamService.create(user.getUUID(), teamCreateRequest.title()));
	}

	@PutMapping
	public ResponseEntity<TeamDetails> updateTeam(
		@AuthenticationPrincipal final CustomOAuth2User user,
		@RequestBody final TeamUpdateRequest teamUpdateRequest
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(teamService.update(
				user.getUUID(),
				teamUpdateRequest.teamId(),
				teamUpdateRequest.newTitle(),
				teamUpdateRequest.notificationDay(),
				teamUpdateRequest.notificationHour()
			));
	}

	@DeleteMapping("/{teamId}")
	public ResponseEntity<Void> deleteTeam(
		@AuthenticationPrincipal final CustomOAuth2User user,
		@PathVariable final Long teamId
	){
		teamService.delete(user.getUUID(), teamId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
