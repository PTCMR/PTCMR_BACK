package soon.PTCMR_Back.global.oauth.dto;

import soon.PTCMR_Back.domain.member.entity.SocialType;

public record UserDTO(
	SocialType provider,
	String providerId,
	String email,
	String name,
	String uuid
) {
	public UserDTO(String uuid) {
		this(null, null, null, null, uuid);
	}
}
