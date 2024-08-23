package soon.PTCMR_Back.global.exception;

import soon.PTCMR_Back.global.entity.ErrorCode;

public class InvalidMemberException extends PTCMRException {

	public InvalidMemberException() {
		super(ErrorCode.INVALID_MEMBER);
	}
}
