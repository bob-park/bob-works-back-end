package org.bobpark.userservice.domain.user.model.vacation;

import java.time.LocalDate;

public record AddAlternativeVacationRequest(LocalDate effectiveDate,
                                            Double effectiveCount,
                                            String effectiveReason) {
}
