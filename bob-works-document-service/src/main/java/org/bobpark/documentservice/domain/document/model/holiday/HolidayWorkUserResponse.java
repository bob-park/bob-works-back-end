package org.bobpark.documentservice.domain.document.model.holiday;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkUser;

@Builder
public record HolidayWorkUserResponse(Long id,
                                      Boolean isManualInput,
                                      Long workUserId,
                                      String workUserName,
                                      LocalDate workDate,
                                      Double totalWorkTime,
                                      Boolean isVacation,
                                      Double paymentTime,
                                      List<HolidayWorkTimeResponse> times) {

    public static HolidayWorkUserResponse toResponse(HolidayWorkUser entity) {
        return HolidayWorkUserResponse.builder()
            .id(entity.getId())
            .isManualInput(entity.getIsManualInput())
            .workUserId(entity.getWorkUserId())
            .workUserName(entity.getWorkUserName())
            .workDate(entity.getWorkDate())
            .totalWorkTime(entity.getTotalWorkTime())
            .isVacation(entity.getIsVacation())
            .paymentTime(entity.getPaymentTime())
            .times(entity.getTimes().stream().map(HolidayWorkTimeResponse::toResponse).toList())
            .build();
    }
}
