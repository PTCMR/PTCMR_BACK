package soon.PTCMR_Back.domain.team.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import soon.PTCMR_Back.domain.team.entity.Team;

public interface TeamJpaRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByInviteCode(String inviteCode);
}
