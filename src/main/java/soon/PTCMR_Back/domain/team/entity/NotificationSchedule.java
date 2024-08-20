package soon.PTCMR_Back.domain.team.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class NotificationSchedule {

    @Column(name = "days")
    private long day;

    @Column(name = "hours")
    private long hour;

    public static NotificationSchedule create() {
        return new NotificationSchedule(7, 12);
    }

    public void updateNotificationTime(long day, long hour) {
        this.day = day;
        this.hour = hour;
    }

    private NotificationSchedule(long day, long hour) {
        this.day = day;
        this.hour = hour;
    }
}