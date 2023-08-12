package org.bobpark.documentservice.domain.document.model.holiday;

import java.time.LocalTime;

public record AddHolidayWorkTime(boolean existBreakTime,
                                 LocalTime startTime,
                                 LocalTime endTime) {
}
