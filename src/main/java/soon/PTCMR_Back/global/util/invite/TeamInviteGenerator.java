package soon.PTCMR_Back.global.util.invite;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class TeamInviteGenerator implements InviteGenerator {

	private static final int INVITE_CODE_LENGTH = 8;

	@Override
	public String createInviteCode() {
		return RandomStringUtils.randomAlphanumeric(INVITE_CODE_LENGTH);
	}
}
