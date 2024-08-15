package soon.PTCMR_Back.global.oauth;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.member.entity.SocialType;
import soon.PTCMR_Back.domain.member.repository.MemberRepository;
import soon.PTCMR_Back.global.oauth.dto.UserDTO;
import soon.PTCMR_Back.global.oauth.provider.OAuth2ProviderFactory;
import soon.PTCMR_Back.global.oauth.provider.dto.OAuth2Response;

@Service
@Slf4j(topic = "OAuth2Service")
@RequiredArgsConstructor
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		log.info(String.valueOf(oAuth2User));

		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		OAuth2Response oAuth2Response = OAuth2ProviderFactory.createOAuth2Response(registrationId,
			oAuth2User.getAttributes());

		Optional<Member> member = Optional.ofNullable(
			memberRepository.findByUuid(oAuth2Response.getUUID()));

		if (member.isEmpty()) {
			createMember(
				oAuth2Response.getName(),
				oAuth2Response.getUUID(),
				oAuth2Response.getProvider()
			);
		} else {
			member.get().update(
				oAuth2Response.getUUID(),
				oAuth2Response.getName(),
				oAuth2Response.getProvider());
		}

		UserDTO userDTO = new UserDTO(
			oAuth2Response.getProvider(),
			oAuth2Response.getName(),
			oAuth2Response.getUUID()
		);

		return new CustomOAuth2User(userDTO);
	}

	private void createMember(String name, String uuid, SocialType provider) {
		// TODO : deviceToken 입력
		memberRepository.save(Member.create(uuid, name, "default", provider));
	}
}
