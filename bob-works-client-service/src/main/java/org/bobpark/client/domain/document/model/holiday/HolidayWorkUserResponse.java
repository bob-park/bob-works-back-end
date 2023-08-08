package org.bobpark.client.domain.document.model.holiday;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

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
}
