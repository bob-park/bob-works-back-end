package org.bobpark.documentservice.domain.user.model.vacation;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record AddAlternativeVacationRequest(LocalDate effectiveDate,
                                            double effectiveCount,
                                            String effectiveReason) {
}
