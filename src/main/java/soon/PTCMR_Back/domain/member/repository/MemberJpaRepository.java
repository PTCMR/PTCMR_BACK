package soon.PTCMR_Back.domain.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import soon.PTCMR_Back.domain.member.entity.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

	Member findByUuid(String uuid);
}
