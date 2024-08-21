package soon.PTCMR_Back.domain.team.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.global.exception.TeamNotFoundException;

@RequiredArgsConstructor
@Repository
public class TeamRepository {

    private final TeamJpaRepository teamJpaRepository;

    public Long save(Team team) {
        return teamJpaRepository.save(team).getId();
    }

    public boolean existsById(Long id) {
        return teamJpaRepository.existsById(id);
    }

    public Team findById(Long id) {
        return teamJpaRepository.findById(id).orElseThrow(
            () -> new TeamNotFoundException()
        );
    }

	public void delete(Team team) {
        teamJpaRepository.delete(team);
	}
}
