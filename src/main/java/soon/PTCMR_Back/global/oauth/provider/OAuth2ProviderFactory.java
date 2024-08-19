package soon.PTCMR_Back.global.oauth.provider;

import java.util.Map;
import soon.PTCMR_Back.global.oauth.provider.dto.GoogleOAuth2Response;
import soon.PTCMR_Back.global.oauth.provider.dto.KakaoOAuth2Response;
import soon.PTCMR_Back.global.oauth.provider.dto.OAuth2Response;

public class OAuth2ProviderFactory {

	public static OAuth2Response createOAuth2Response(String registrationId, Map<String, Object> attributes) {
		OAuth2Response oAuth2Response = null;

		switch (registrationId) {
			case "google" -> oAuth2Response = new GoogleOAuth2Response(attributes);
			case "kakao" -> oAuth2Response = new KakaoOAuth2Response(attributes);
		}

		return oAuth2Response;
	}
}
