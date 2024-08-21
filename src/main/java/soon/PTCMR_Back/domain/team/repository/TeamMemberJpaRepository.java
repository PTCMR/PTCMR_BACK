package soon.PTCMR_Back.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.entity.TeamMember;

public interface TeamMemberJpaRepository extends JpaRepository<TeamMember, Long> {

	boolean existsByTeamAndMember(Team team, Member member);
}
