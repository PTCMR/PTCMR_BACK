package soon.PTCMR_Back.global.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    TEST(400, "테스트용 에러");

    private final int status;
    private final String message;
}