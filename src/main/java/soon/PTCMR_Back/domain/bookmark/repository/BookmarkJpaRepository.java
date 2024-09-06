package soon.PTCMR_Back.domain.bookmark.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import soon.PTCMR_Back.domain.bookmark.entity.Bookmark;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.team.entity.Team;

public interface BookmarkJpaRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByMemberAndTeam(Member member, Team team);
}
