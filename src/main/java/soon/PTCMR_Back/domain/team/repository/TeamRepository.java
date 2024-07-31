package soon.PTCMR_Back.domain.team.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.PTCMR_Back.domain.team.entity.Team;

@RequiredArgsConstructor
@Repository
public class TeamRepository {

    private final TeamJpaRepository teamJpaRepository;

    public void save(Team team) {
        teamJpaRepository.save(team);
    }

    public boolean existsById(Long id) {
        return teamJpaRepository.existsById(id);
    }
}
