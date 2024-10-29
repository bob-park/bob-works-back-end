package org.bobpark.client.domain.document.model.holiday;

import java.time.LocalTime;

import lombok.Builder;

@Builder
public record HolidayWorkTimeResponse(Long id,
                                      boolean existBreakTime,
                                      LocalTime startTime,
                                      LocalTime endTime) {
}
