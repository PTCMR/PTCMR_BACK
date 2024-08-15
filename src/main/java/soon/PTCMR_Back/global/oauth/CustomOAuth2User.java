package soon.PTCMR_Back.global.oauth;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import soon.PTCMR_Back.domain.member.entity.SocialType;
import soon.PTCMR_Back.global.oauth.dto.UserDTO;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

	private final UserDTO userDTO;

	@Override
	public Map<String, Object> getAttributes() {
		return Map.of();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getName() {
		return userDTO.name();
	}

	public SocialType getProvider() {
		return userDTO.provider();
	}

	public String getUUID(){
		return userDTO.uuid();
	}
}
