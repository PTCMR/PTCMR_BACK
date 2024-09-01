package soon.PTCMR_Back.domain.team.data;

import soon.PTCMR_Back.domain.team.entity.Team;

public class TeamData {

	public static final String TEAM_TITLE = "testTitle";
	public static final String INVITE_CODE = "testInviteCode";

	public static Team createTeam(){
		return Team.create(TEAM_TITLE, INVITE_CODE);
	}

	public static Team createTeam(String inviteCode){
		return Team.create(TEAM_TITLE, inviteCode);
	}
}
