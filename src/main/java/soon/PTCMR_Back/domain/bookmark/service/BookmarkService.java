package soon.PTCMR_Back.domain.bookmark.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.PTCMR_Back.domain.bookmark.entity.Bookmark;
import soon.PTCMR_Back.domain.bookmark.repository.BookmarkRepository;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.member.repository.MemberRepository;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.repository.TeamRepository;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public Long create(String uuid, Long teamId) {
        Member member = memberRepository.findByUuid(uuid);
        Team team = teamRepository.findById(teamId);
        Long result;

        Optional<Bookmark> bookmark = bookmarkRepository.findByMemberAndTeam(member, team);

        if (bookmark.isEmpty()) {
            result = bookmarkRepository.save(Bookmark.create(team, member));
        } else {
            bookmark.get().reBookmark();
            result = bookmark.get().getId();
        }

        return result;
    }
}
