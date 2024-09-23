package soon.PTCMR_Back.global.exception;

import soon.PTCMR_Back.global.entity.ErrorCode;

public class CannotModifyDefaultCategoryException extends PTCMRException {

    public CannotModifyDefaultCategoryException() {
        super(ErrorCode.CANNOT_MODIFY_DEFAULT_CATEGORY);
    }
}
