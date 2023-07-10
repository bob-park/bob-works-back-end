package org.bobpark.client.domain.user.model.vacation;

import java.time.LocalDate;

public record UserAlternativeVacationResponse(Long id,
                                              LocalDate effectiveDate,
                                              Double effectiveCount,
                                              String effectiveReason,
                                              Double usedCount) {
}
