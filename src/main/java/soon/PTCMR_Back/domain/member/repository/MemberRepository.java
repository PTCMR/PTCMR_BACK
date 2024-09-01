package soon.PTCMR_Back.domain.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.PTCMR_Back.domain.member.entity.Member;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	private final MemberJpaRepository jpaRepository;

	public Member save(Member member) {
		return jpaRepository.save(member);
	}

	public Member findByUuid(String uuid) {
		return jpaRepository.findByUuid(uuid);
	}

	public void deleteAll() {
		jpaRepository.deleteAll();
	}

	public boolean existByUuid(String uuid) {
		return jpaRepository.existsByUuid(uuid);
	}
}
