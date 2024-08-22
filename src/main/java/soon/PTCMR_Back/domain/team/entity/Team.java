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

    @Embedded
    private NotificationSchedule schedule;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleted;

    public static Team create(String title) {
        return Team.builder()
            .title(title)
            .schedule(NotificationSchedule.create())
            .build();
    }

    public void update(String newTitle, long notificationDay, long notificationHour) {
        this.title = newTitle;
        this.schedule.updateNotificationTime(notificationDay, notificationHour);
    }

    @Builder
    private Team(String title, NotificationSchedule schedule) {
        this.title = title;
        this.schedule = schedule;
    }

}
