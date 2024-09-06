package soon.PTCMR_Back.domain.bookmark.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import soon.PTCMR_Back.domain.member.entity.Member;
import soon.PTCMR_Back.domain.team.entity.Team;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE Bookmark SET deleted = true WHERE id=?")
@Getter
@Entity
public class Bookmark{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleted;

    public static Bookmark create(Team team, Member member) {
        return new Bookmark(team, member);
    }

    private Bookmark(Team team, Member member) {
        this.team = team;
        this.member = member;
    }

    public void reBookmark() {
        deleted = true;
    }
}
