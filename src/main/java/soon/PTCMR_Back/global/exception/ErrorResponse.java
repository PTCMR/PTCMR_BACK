package soon.PTCMR_Back.global.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;

public record ErrorResponse(int status, String message, Map<String, String> validation) {

    @Builder
    public ErrorResponse(int status, String message, Map<String, String> validation) {
        this.status = status;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}

