package org.bobpark.documentservice.domain.user.model.vacation;

import lombok.Builder;

@Builder
public record VacationResponse(double totalCount,
                               double usedCount) {
}