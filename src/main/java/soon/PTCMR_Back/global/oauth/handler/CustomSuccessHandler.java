package soon.PTCMR_Back.global.oauth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import soon.PTCMR_Back.global.oauth.CustomOAuth2User;
import soon.PTCMR_Back.global.oauth.jwt.JwtProvider;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtProvider jwtProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

		String uuid = customOAuth2User.getUUID();

		String token = jwtProvider.createToken(uuid);

		response.addHeader(JwtProvider.AUTHORIZATION_HEADER, token);
	}
}
