package soon.PTCMR_Back.global.oauth.provider.dto;

import java.util.Map;
import soon.PTCMR_Back.domain.member.entity.SocialType;

public class KakaoOAuth2Response implements OAuth2Response {

	private final Map<String, Object> attributes;
	private final Map<String, Object> profile;

	public KakaoOAuth2Response(Map<String, Object> attributes) {
		this.attributes = attributes;
		Map<String, Object> map = (Map<String, Object>) attributes.get("kakao_account");
		this.profile = (Map<String, Object>) map.get("profile");
	}

	@Override
	public SocialType getProvider() {
		return SocialType.KAKAO;
	}

	@Override
	public String getProviderId() {
		return attributes.get("id").toString();
	}

	@Override
	public String getName() {
		return profile.get("nickname").toString();
	}

	@Override
	public String getUUID() {
		return getProvider() + " " + getProviderId();
	}
}
