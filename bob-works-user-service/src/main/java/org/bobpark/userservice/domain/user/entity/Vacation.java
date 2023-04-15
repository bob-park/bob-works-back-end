package org.bobpark.userservice.domain.user.entity;

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
    private Vacation(double totalCount, double usedCount) {
        this.totalCount = totalCount;
        this.usedCount = usedCount;
    }

    public void useVacation(double count) {
        if (getUsedCount() + count > totalCount) {
            throw new OverUsableVacationException(getTotalCount(), (getUsedCount() + count));
        }

        this.usedCount += 1;
    }

    public void cancelVacation(double count) {
        if (getUsedCount() - count < 0) {
            throw new IllegalArgumentException("Invalid cancel request. (current=" + getUsedCount() + ")");
        }

        this.usedCount -= 1;
    }
}
