package soon.PTCMR_Back.global.oauth.dto;

import lombok.Builder;
import soon.PTCMR_Back.domain.member.entity.SocialType;

@Builder
public record UserDTO(
	SocialType provider,
	String name,
	String uuid
) {

	public UserDTO(String uuid) {
		this(null, null, uuid);
	}
}
