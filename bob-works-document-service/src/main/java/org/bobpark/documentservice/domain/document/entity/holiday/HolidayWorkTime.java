package org.bobpark.documentservice.domain.document.entity.holiday;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.*;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.bobpark.documentservice.common.entity.BaseEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "holiday_work_times")
public class HolidayWorkTime extends BaseEntity {

    // 점심 시간
    private static final LocalTime START_REST_TIME = LocalTime.of(12, 0);
    private static final LocalTime END_REST_TIME = LocalTime.of(13, 0);

    // 근무시간
    private static final LocalTime START_WORK_TIME = LocalTime.of(6, 0);
    private static final LocalTime END_WORK_TIME = LocalTime.of(22, 0);

    // 야간 근무 시간
    private static final LocalTime START_NIGHT_WORK_TIME = LocalTime.of(22, 0);
    private static final LocalTime END_NIGHT_WORK_TIME = LocalTime.of(6, 0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_users_id")
    private HolidayWorkUser user;

    private Boolean existBreakTime;

    private LocalTime startTime;
    private LocalTime endTime;

    @Builder
    private HolidayWorkTime(Long id, Boolean existBreakTime, LocalTime startTime, LocalTime endTime) {

        checkArgument(isNotEmpty(startTime), "startTime must be provided.");
        checkArgument(isNotEmpty(endTime), "endTime must be provided.");
        checkArgument(startTime.isBefore(endTime), "startTime must be less than endTime.");

        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.existBreakTime = defaultIfNull(existBreakTime, false);
    }

    public void setUser(HolidayWorkUser workUser) {
        this.user = workUser;
    }

    /**
     * totalWorkTime 계산식
     * - 주말인 경우
     * 1. work time 에 12:00 ~ 13:00 가 포함되어 있는 경우 해당 시간을 점심시간으로 계산하여 1시간을 뺀다.
     * 2. 09:00 ~ 18:00 사이인 경우 1.5배 하여 계산
     * 3. 22:00 ~ 09:00 사이인 경우 2배로 하여 계산
     *
     * @return 계산된 work time
     */
    public double getCalculateWorkTime() {

        double result = 0;
        int calculateMinute = getEndTime().getMinute() - getStartTime().getMinute();
        int hour = getEndTime().getHour() - getStartTime().getHour();

        if (hour < 0) {
            hour--;
        }

        double minute = 0;

        if (Math.abs(calculateMinute) != 0) {
            minute = (double)Math.abs(calculateMinute) / (double)60;
        }

        result += (hour + minute);

        double bonus = 1.5;

        if (isRestTime(getStartTime(), getEndTime())) {
            result--;
        }

        if (isNightWorkTime(getStartTime(), getEndTime())) {
            bonus = 2;
        }

        return result * bonus;
    }

    private boolean isRestTime(LocalTime startTime, LocalTime endTime) {

        if (!getExistBreakTime()) {
            return false;
        }

        return START_REST_TIME.isAfter(startTime) && END_REST_TIME.isBefore(endTime);
    }

    private boolean isWorkTime(LocalTime startTime, LocalTime endTime) {
        return START_WORK_TIME.isAfter(startTime) && END_WORK_TIME.isBefore(endTime);
    }

    private boolean isNightWorkTime(LocalTime startTime, LocalTime endTime) {
        return (START_NIGHT_WORK_TIME.isAfter(startTime) || END_NIGHT_WORK_TIME.isBefore(startTime))
            && (END_NIGHT_WORK_TIME.isAfter(endTime));
    }
}
