package soon.PTCMR_Back.domain.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.member.repository.MemberRepository;
import soon.PTCMR_Back.domain.team.dto.response.TeamDetails;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.event.TeamCreateEvent;
import soon.PTCMR_Back.domain.team.repository.TeamRepository;
import soon.PTCMR_Back.global.util.invite.InviteGenerator;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamManager teamManager;
    private final InviteGenerator inviteGenerator;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Long create(String uuid, String title) {
        Member member = memberRepository.findByUuid(uuid);
        Team team = Team.create(title, inviteGenerator.createInviteCode());

        Long savedTeamId = teamRepository.save(team);
        teamManager.joinTeam(team, member);

        eventPublisher.publishEvent(new TeamCreateEvent(savedTeamId));

        return savedTeamId;
    }

    @Transactional
    public TeamDetails update(String uuid, Long teamId, String newTitle, long notificationDay, long notificationHour) {
        Member member = memberRepository.findByUuid(uuid);
        Team team = teamRepository.findById(teamId);

        teamManager.validateTeamAccess(team, member);
        team.update(newTitle, notificationDay, notificationHour);


        return TeamDetails.from(team);
    }

    @Transactional
	public void delete(String uuid, Long teamId) {
        Member member = memberRepository.findByUuid(uuid);
        Team team = teamRepository.findById(teamId);

        teamManager.validateTeamAccess(team, member);
        teamRepository.delete(team);
	}

    @Transactional
    public void invite(String uuid, String inviteCode) {
        Member member = memberRepository.findByUuid(uuid);
        Team team = teamRepository.findByInviteCode(inviteCode);

        teamManager.verifyMemberInTeam(team, member);
        teamManager.joinTeam(team, member);
    }
}
