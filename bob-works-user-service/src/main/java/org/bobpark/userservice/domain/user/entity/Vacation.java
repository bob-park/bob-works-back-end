package org.bobpark.userservice.domain.user.entity;

import static org.apache.commons.lang3.ObjectUtils.*;

import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.bobpark.userservice.exception.user.vacation.OverUsableVacationException;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Vacation {

    private double totalCount;
    private double usedCount;

    @Builder
    private Vacation(Double totalCount, Double usedCount) {
        this.totalCount = defaultIfNull(totalCount, 0.0);
        this.usedCount = defaultIfNull(usedCount, 0.0);
    }

    public void useVacation(double count) {
        if (getUsedCount() + count > totalCount) {
            throw new OverUsableVacationException(getTotalCount(), (getUsedCount() + count));
        }

        this.usedCount += count;
    }

    public void cancelVacation(double count) {
        if (getUsedCount() - count < 0) {
            throw new IllegalArgumentException("Invalid cancel request. (current=" + getUsedCount() + ")");
        }

        this.usedCount -= count;
    }

    public void addTotalCount(double add) {
        this.totalCount += add;
    }
}
