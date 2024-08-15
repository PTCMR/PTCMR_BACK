package soon.PTCMR_Back.global.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soon.PTCMR_Back.global.entity.ErrorCode;

@Getter
@RequiredArgsConstructor
public abstract class PTCMRException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    private final ErrorCode errorCode;
}
