package soon.PTCMR_Back.domain.bookmark.repository;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.PTCMR_Back.domain.bookmark.entity.Bookmark;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.global.exception.BookmarkNotFoundException;

@Repository
@RequiredArgsConstructor
public class BookmarkRepository {

    private final BookmarkJpaRepository bookmarkJpaRepository;

    public Long save(Bookmark bookmark) {
        return bookmarkJpaRepository.save(bookmark).getId();
    }

    public Bookmark findById(Long id) {
        return bookmarkJpaRepository.findById(id).orElseThrow(
            BookmarkNotFoundException::new
        );
    }

    public Optional<Bookmark> findByMemberAndTeam(Member member, Team team) {
        return bookmarkJpaRepository.findByMemberAndTeam(member, team);
    }
}
