package soon.PTCMR_Back.global.oauth.provider.dto;

import soon.PTCMR_Back.domain.member.entity.SocialType;

public interface OAuth2Response {
	SocialType getProvider();
	String getProviderId();
	String getName();
	String getEmail();
	String getUUID();
}
