package soon.PTCMR_Back.global.exception;

import soon.PTCMR_Back.global.entity.ErrorCode;

public class MemberNotFoundException extends PTCMRException{

	public MemberNotFoundException() {
		super(ErrorCode.MEMBER_NOT_FOUND);
	}
}
