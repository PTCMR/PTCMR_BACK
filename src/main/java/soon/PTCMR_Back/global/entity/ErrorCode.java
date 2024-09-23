package soon.PTCMR_Back.global.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	TEAM_NOT_FOUND(404, "존재하지 않는 팀입니다."),
	MEMBER_NOT_FOUND(404, "존재하지 않는 멤버입니다."),
	CATEGORY_NOT_FOUND(404, "존재하지 않는 카테고리입니다."),
	AUTHORIZATION_DENIED(404, "접근 권한이 없습니다."),
    PRODUCT_NOT_FOUND(404, "존재하지 않는 상품입니다."),
	INVALID_MEMBER(403, "팀에 접근 권한이 없습니다." ),
	TEAM_MEMBER_EXIST(404, "이미 참여 중인 팀입니다."),
	CATEGORY_EXIST(404, "이미 존재하는 카테고리입니다."),
	CANNOT_MODIFY_DEFAULT_CATEGORY(400, "기본 카테고리는 수정할 수 없습니다.");

    private final int status;
    private final String message;
}