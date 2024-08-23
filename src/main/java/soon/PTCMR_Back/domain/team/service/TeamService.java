package soon.PTCMR_Back.domain.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.member.repository.MemberRepository;
import soon.PTCMR_Back.domain.team.dto.response.TeamDetails;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.repository.TeamRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamManager teamManager;

    @Transactional
    public Long create(String uuid, String title) {
        Member member = memberRepository.findByUuid(uuid);
        Team team = Team.create(title);

        Long result = teamRepository.save(team);

        teamManager.joinTeam(team, member);

        return result;
    }

    @Transactional
    public TeamDetails update(Long teamId, String newTitle, long notificationDay, long notificationHour, String uuid) {
        Team team = teamRepository.findById(teamId);
        team.update(newTitle, notificationDay, notificationHour);


        return TeamDetails.from(team);
    }

    @Transactional
	public void delete(Long teamId, String uuid) {
        Team team = teamRepository.findById(teamId);
        Member member = memberRepository.findByUuid(uuid);

        teamManager.verifyMemberInTeam(team, member);
        teamRepository.delete(team);
	}
}
