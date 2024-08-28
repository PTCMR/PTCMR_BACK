package soon.PTCMR_Back.global.exception;

import soon.PTCMR_Back.global.entity.ErrorCode;

public class TeamMemberExistException extends PTCMRException {

    public TeamMemberExistException() {
        super(ErrorCode.TEAM_MEMBER_EXIST);
    }

}
