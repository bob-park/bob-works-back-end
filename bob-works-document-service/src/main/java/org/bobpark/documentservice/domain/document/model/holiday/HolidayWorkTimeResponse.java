package org.bobpark.documentservice.domain.document.model.holiday;

import java.time.LocalTime;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkTime;

@Builder
public record HolidayWorkTimeResponse(LocalTime startTime,
                                      LocalTime endTime,
                                      boolean existBreakTime) {

    public static HolidayWorkTimeResponse toResponse(HolidayWorkTime entity) {
        return HolidayWorkTimeResponse.builder()
            .startTime(entity.getStartTime())
            .endTime(entity.getEndTime())
            .existBreakTime(entity.getExistBreakTime())
            .build();
    }
}
