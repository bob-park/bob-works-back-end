package org.bobpark.documentservice.domain.user.model.vacation;

import java.util.List;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.type.VacationType;

@Builder
public record CancelUserVacationRequest(VacationType type,
                                        List<Long> cancelAlternativeVacationIds,
                                        double cancelCount) {
}
