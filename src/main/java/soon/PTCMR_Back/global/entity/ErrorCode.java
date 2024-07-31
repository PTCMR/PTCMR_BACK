package soon.PTCMR_Back.global.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    TEAM_NOT_FOUND(404, "존재하지 않는 팀입니다.");

    private final int status;
    private final String message;
}