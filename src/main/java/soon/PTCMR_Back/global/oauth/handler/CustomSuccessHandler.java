package soon.PTCMR_Back.global.oauth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import soon.PTCMR_Back.global.oauth.CustomOAuth2User;
import soon.PTCMR_Back.global.oauth.jwt.JwtProvider;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtProvider jwtProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

		String uuid = customOAuth2User.getUUID();

		String token = jwtProvider.createToken(uuid);

		log.info(token);
		response.addHeader(JwtProvider.AUTHORIZATION_HEADER, token);
		//TODO : 프론트 구현 후 바꾸기
		response.sendRedirect("http://localhost:8080");
	}
}
