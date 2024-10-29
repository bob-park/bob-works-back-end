package org.bobpark.documentservice.domain.document.entity.holiday;

import static com.google.common.base.Preconditions.*;

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

import org.apache.commons.lang3.StringUtils;

import org.bobpark.documentservice.common.entity.BaseEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "holiday_work_times_logs")
public class HolidayWorkTimeLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_id")
    private HolidayWorkTime workTime;

    private String calculationLog;

    @Builder
    private HolidayWorkTimeLog(Long id, String calculationLog) {

        checkArgument(StringUtils.isNotBlank(calculationLog), "calculationLog must be provided.");

        this.id = id;
        this.calculationLog = calculationLog;
    }

    /*
     * 편의 메서드
     */
    public void setWorkTime(HolidayWorkTime workTime) {
        this.workTime = workTime;
    }
}
