package soon.PTCMR_Back.global.exception;

import soon.PTCMR_Back.global.entity.ErrorCode;

public class AuthorizationDeniedException extends PTCMRException{

	public AuthorizationDeniedException() {
		super(ErrorCode.AUTHORIZATION_DENIED);
	}
}
