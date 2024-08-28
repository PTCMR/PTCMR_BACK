package soon.PTCMR_Back.domain.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.member.repository.MemberRepository;
import soon.PTCMR_Back.domain.team.dto.response.TeamDetails;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.repository.TeamRepository;
import soon.PTCMR_Back.global.exception.MemberNotFoundException;

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
    public TeamDetails update(String uuid, Long teamId, String newTitle, long notificationDay, long notificationHour) {
        if(!memberRepository.existByUuid(uuid)){
            throw new MemberNotFoundException();
        }

        Team team = teamRepository.findById(teamId);
        team.update(newTitle, notificationDay, notificationHour);


        return TeamDetails.from(team);
    }

    @Transactional
	public void delete(String uuid, Long teamId) {
        Member member = memberRepository.findByUuid(uuid);
        Team team = teamRepository.findById(teamId);

        teamManager.verifyMemberInTeam(team, member);
        teamRepository.delete(team);
	}

    @Transactional
    public void invite(String uuid, String inviteCode) {
        Member member = memberRepository.findByUuid(uuid);
        Team team = teamRepository.findByInviteCode(inviteCode);

        teamManager.validateMemberInTeam(team, member);
        teamManager.joinTeam(team, member);
    }
}
