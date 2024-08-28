package soon.PTCMR_Back.global.util.invite;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class InviteCodeGeneratorTest {

	InviteGenerator inviteGenerator = new InviteCodeGenerator();
	static Set<String> set = ConcurrentHashMap.newKeySet();

	@Test
	@DisplayName("단일 테스트")
	void succeed() {
		System.out.println(inviteGenerator.createInviteCode());
	}

	@RepeatedTest(10)
	@DisplayName("10만번 돌렸을 때 중복 확인")
	void test1() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		for (int i = 0; i < 10000; i++) {
			executorService.submit(() -> {
					set.add(inviteGenerator.createInviteCode());
				}
			);
		}

		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.MINUTES);
		System.out.println(set.size());
	}

}