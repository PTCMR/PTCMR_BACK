package soon.PTCMR_Back.domain.team.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class NotificationSchedule {

    private long day;

    private long hour;

    private NotificationSchedule(long day, long hour) {
        this.day = day;
        this.hour = hour;
    }
}