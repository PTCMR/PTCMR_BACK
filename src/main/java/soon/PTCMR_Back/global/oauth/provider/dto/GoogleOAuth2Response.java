package soon.PTCMR_Back.global.oauth.provider.dto;

import java.util.Map;
import soon.PTCMR_Back.domain.member.entity.SocialType;

public class GoogleOAuth2Response implements OAuth2Response {

	private final Map<String, Object> attributes;

	public GoogleOAuth2Response(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public SocialType getProvider() {
		return SocialType.GOOGLE;
	}

	@Override
	public String getProviderId() {
		return attributes.get("sub").toString();
	}

	@Override
	public String getName() {
		return attributes.get("name").toString();
	}

	@Override
	public String getUUID() {
		return getProvider() + " " + getProviderId();
	}
}
