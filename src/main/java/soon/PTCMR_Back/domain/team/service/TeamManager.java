package soon.PTCMR_Back.domain.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.entity.TeamMember;
import soon.PTCMR_Back.domain.team.repository.TeamMemberRepository;

@Service
@RequiredArgsConstructor
public class TeamManager {

	private final TeamMemberRepository teamMemberRepository;

	public Long joinTeam(Team team, Member member) {
		Long result = teamMemberRepository.save(TeamMember.create(team, member));

		return result;
	}
}
