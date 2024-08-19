package soon.PTCMR_Back.global.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	TEAM_NOT_FOUND(404, "존재하지 않는 팀입니다."),
	MEMBER_NOT_FOUND(404, "존재하지 않는 멤버입니다."),
	AUTHORIZATION_DENIED(404, "접근 권한이 없습니다."),
    PRODUCT_NOT_FOUND(404, "존재하지 않는 상품입니다.");

    private final int status;
    private final String message;
}