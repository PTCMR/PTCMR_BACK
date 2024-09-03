package soon.PTCMR_Back.global.exception;

import soon.PTCMR_Back.global.entity.ErrorCode;

public class CategoryExistException extends PTCMRException {

    public CategoryExistException() {
        super(ErrorCode.CATEGORY_EXIST);
    }
}
