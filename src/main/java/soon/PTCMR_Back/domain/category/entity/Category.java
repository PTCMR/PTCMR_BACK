package soon.PTCMR_Back.domain.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.global.exception.CannotModifyDefaultCategoryException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public Category(String title, Team team) {
        this.title = title;
        this.team = team;
    }

    public static Category create(String title, Team team) {
        return Category.builder()
            .title(title)
            .team(team)
            .build();
    }

    public void update(String title) throws CannotModifyDefaultCategoryException {
        validateTitleNotDefault();
        this.title = title;
    }

    private void validateTitleNotDefault() throws CannotModifyDefaultCategoryException {
        if (this.title.equals("기본")) {
            throw new CannotModifyDefaultCategoryException();
        }
    }
}
