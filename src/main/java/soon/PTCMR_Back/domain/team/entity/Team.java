package soon.PTCMR_Back.domain.team.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import soon.PTCMR_Back.global.entity.BaseTimeEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE Team SET deleted = true WHERE id=?")
@SQLRestriction("deleted = false")
@Getter
@Entity
public class Team extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String inviteCode;

    @Embedded
    private NotificationSchedule schedule;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleted;

    public static Team create(String title, String inviteCode) {
        return Team.builder()
            .title(title)
            .inviteCode(inviteCode)
            .schedule(NotificationSchedule.create())
            .build();
    }

    public void update(String newTitle, long notificationDay, long notificationHour) {
        this.title = newTitle;
        this.schedule.updateNotificationTime(notificationDay, notificationHour);
    }

    @Builder
    private Team(String title, String inviteCode, NotificationSchedule schedule) {
        this.title = title;
        this.inviteCode = inviteCode;
        this.schedule = schedule;
    }

}
