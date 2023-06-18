package org.bobpark.userservice.domain.user.entity.vacation;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.apache.commons.lang.StringUtils;

import org.bobpark.userservice.common.entity.BaseEntity;
import org.bobpark.userservice.domain.user.entity.User;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users_alternative_vacations")
public class UserAlternativeVacation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate effectiveDate;
    private Double effectiveCount;
    private String effectiveReason;
    private Double useCount;

    public UserAlternativeVacation(Long id, LocalDate effectiveDate, Double effectiveCount, String effectiveReason) {

        checkArgument(isNotEmpty(effectiveDate), "effectiveDate must be provided.");
        checkArgument(isNotEmpty(effectiveCount), "effectiveCount must be provided.");
        checkArgument(StringUtils.isNotBlank(effectiveReason), "effectiveReason must be provided.");

        this.id = id;
        this.effectiveDate = effectiveDate;
        this.effectiveCount = effectiveCount;
        this.effectiveReason = effectiveReason;
        this.useCount = 0.0;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void use(double useCount) {

        checkArgument(getEffectiveCount() > (getUseCount() + useCount), "useCount must be less then effectiveCount.");

        this.useCount += useCount;
    }

    public void cancel(double cancelCount) {

        checkArgument(getEffectiveCount() > (getUseCount() + useCount), "cancelCount must be greater then zero.");

        this.useCount += cancelCount;

    }
}
