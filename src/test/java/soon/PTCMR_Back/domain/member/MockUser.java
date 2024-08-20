package soon.PTCMR_Back.domain.member;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import soon.PTCMR_Back.domain.member.entity.SocialType;
import soon.PTCMR_Back.global.oauth.CustomOAuth2User;
import soon.PTCMR_Back.global.oauth.dto.UserDTO;

public class MockUser {

	public static void createMockUser(){
		CustomOAuth2User customOAuth2User = new CustomOAuth2User(
			UserDTO.builder()
				.name("test")
				.provider(SocialType.KAKAO)
				.uuid("kakao 123456")
				.build()
		);

		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = new UsernamePasswordAuthenticationToken(customOAuth2User, null, null);
		context.setAuthentication(auth);
	}
}
