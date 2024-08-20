package soon.PTCMR_Back.domain.team.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soon.PTCMR_Back.domain.team.dto.reqeust.TeamCreateRequest;
import soon.PTCMR_Back.domain.team.service.TeamService;
import soon.PTCMR_Back.global.oauth.CustomOAuth2User;

@RestController
@RequestMapping("/api/v1/team")
@RequiredArgsConstructor
public class TeamController {

	private final TeamService teamService;

	@PostMapping
	public ResponseEntity<Long> createTeam(
		@RequestBody final TeamCreateRequest teamCreateRequest,
		@AuthenticationPrincipal CustomOAuth2User user
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(teamService.create(teamCreateRequest.title(), user.getUUID()));
	}
}
