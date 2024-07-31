package soon.PTCMR_Back.global.exception;

import soon.PTCMR_Back.global.entity.ErrorCode;

public class TeamNotFoundException extends PTCMRException {

    public TeamNotFoundException() {
        super(ErrorCode.TEAM_NOT_FOUND);
    }
}
