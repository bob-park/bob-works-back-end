package org.bobpark.documentservice.domain.document.entity.holiday;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "holiday_work_users")
public class HolidayWorkUser extends BaseEntity {

    public static final int VACATION_TIME = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "holiday_work_id")
    private HolidayWorkReport report;

    private Boolean isManualInput;
    private Long workUserId;
    private String workUserName;
    private LocalDate workDate;
    private double totalWorkTime;
    private Boolean isVacation;
    private double paymentTime;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HolidayWorkTime> times = new ArrayList<>();

    @Builder
    private HolidayWorkUser(Long id, Boolean isManualInput, Long workUserId, String workUserName, LocalDate workDate,
        Boolean isVacation) {

        checkArgument(StringUtils.isNotBlank(workUserName), "workUserName must be provided.");
        checkArgument(isNotEmpty(workDate), "workDate must be provided.");
        checkArgument(isNotEmpty(isVacation), "isVacation must be provided.");
        checkArgument(isNotEmpty(paymentTime), "paymentTime must be provided.");

        this.id = id;
        this.isManualInput = defaultIfNull(isManualInput, false);
        this.workUserId = workUserId;
        this.workUserName = workUserName;
        this.workDate = workDate;
        this.isVacation = isVacation;
    }

    public void setReport(HolidayWorkReport report) {
        this.report = report;
    }

    public void addTime(LocalTime startTime, LocalTime endTime) {
        HolidayWorkTime workTime =
            HolidayWorkTime.builder()
                .startTime(startTime)
                .endTime(endTime)
                .build();

        workTime.setUser(this);

        getTimes().add(workTime);
    }

    /**
     * work time 을 계산하는 메서드
     * <p>
     * isVacation 이 true 인 경우 totalWorkTime 에서 8 배수로 뺸 나머지를 paymentTime 에 추가한다.
     */
    public void calculateWorkTime() {

        for (HolidayWorkTime time : times) {
            this.totalWorkTime += time.getCalculateWorkTime();
        }

        this.paymentTime = getTotalWorkTime();

        if (getIsVacation()) {
            this.paymentTime = getTotalWorkTime() % VACATION_TIME;
        }

        if (paymentTime == getTotalWorkTime()) {
            this.isVacation = false;
        }
    }

    public boolean isManualInput() {
        return Boolean.TRUE.equals(getIsManualInput());
    }

    public boolean isVacation() {
        return Boolean.TRUE.equals(getIsVacation());
    }

}
