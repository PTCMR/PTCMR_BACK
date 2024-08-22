package soon.PTCMR_Back.domain.team.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.entity.TeamMember;

@Repository
@RequiredArgsConstructor
public class TeamMemberRepository {

	private final TeamMemberJpaRepository teamMemberJpaRepository;

	public Long save(TeamMember teamMember) {
		return teamMemberJpaRepository.save(teamMember).getId();
	}

	public boolean existByTeamAndMember(Team team, Member member) {
		return teamMemberJpaRepository.existsByTeamAndMember(team, member);
	}
}
