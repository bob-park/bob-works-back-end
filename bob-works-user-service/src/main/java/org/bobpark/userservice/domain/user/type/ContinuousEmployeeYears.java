package org.bobpark.userservice.domain.user.type;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public enum ContinuousEmployeeYears {
    ONE(1, 0),
    THREE(3, 1),
    FIVE(5, 2),
    SEVEN(7, 3),
    TEN(10, 4),
    TWELVE(12, 5),
    FOURTEEN(14, 6),
    FIFTEEN(15, 7),
    SEVENTEEN(17, 8),
    TWENTY(20, 9),
    TWENTY_FIVE(25, 10);

    private final int years;
    private final int incrementCount;

    ContinuousEmployeeYears(int years, int incrementCount) {
        this.years = years;
        this.incrementCount = incrementCount;
    }
}
