package soon.PTCMR_Back.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soon.PTCMR_Back.domain.team.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
