package org.bobpark.documentservice.domain.document.entity.holiday;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_users_id")
    private HolidayWorkUser user;

    private LocalTime startTime;
    private LocalTime endTime;

    @Builder
    private HolidayWorkTime(Long id, LocalTime startTime, LocalTime endTime) {

        checkArgument(isNotEmpty(startTime), "startTime must be provided.");
        checkArgument(isNotEmpty(endTime), "endTime must be provided.");
        checkArgument(startTime.isBefore(endTime), "startTime must be less than endTime.");

        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setUser(HolidayWorkUser workUser) {
        this.user = workUser;
    }
}
