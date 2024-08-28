package soon.PTCMR_Back.domain.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.entity.TeamMember;
import soon.PTCMR_Back.domain.team.repository.TeamMemberRepository;
import soon.PTCMR_Back.global.exception.InvalidMemberException;
import soon.PTCMR_Back.global.exception.TeamMemberExistException;

@Service
@RequiredArgsConstructor
public class TeamManager {

	private final TeamMemberRepository teamMemberRepository;

	public Long joinTeam(Team team, Member member) {
		Long result = teamMemberRepository.save(TeamMember.create(team, member));

		return result;
	}

	public void validateTeamAccess(Team team, Member member) {
		if(!teamMemberRepository.existByTeamAndMember(team, member)){
			throw new InvalidMemberException();
		}
	}

	public void verifyMemberInTeam(Team team, Member member) {
		if(teamMemberRepository.existByTeamAndMember(team, member)){
			throw new TeamMemberExistException();
		}
	}
}
