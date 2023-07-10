package org.bobpark.documentservice.domain.document.model.holiday;

import java.time.LocalDate;
import java.util.List;

public record AddHolidayWorkUserRequest(boolean isManualInput,
                                        Long workUserId,
                                        String workUserName,
                                        LocalDate workDate,
                                        boolean isVacation,
                                        double paymentTime,
                                        List<AddHolidayWorkTime> times) {
}
