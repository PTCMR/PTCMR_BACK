package soon.PTCMR_Back.global.oauth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import soon.PTCMR_Back.global.oauth.dto.UserDTO;
import soon.PTCMR_Back.global.oauth.jwt.JwtProvider;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String token = jwtProvider.getTokenFromHeader(request);

		if (Objects.isNull(token)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		try {
			jwtProvider.validateToken(token);
			String uuid = jwtProvider.getMemberInfoFromToken(token);

			UserDTO userDTO = new UserDTO(uuid);

			CustomOAuth2User oAuth2User = new CustomOAuth2User(userDTO);

			Authentication auth = new UsernamePasswordAuthenticationToken(oAuth2User, null, null);
			SecurityContextHolder.getContext().setAuthentication(auth);

			filterChain.doFilter(request, response);
		} catch (RuntimeException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
