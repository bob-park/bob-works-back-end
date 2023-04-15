package org.bobpark.documentservice.domain.user.model.vacation;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.type.VacationType;

@Builder
public record CancelUserVacationRequest(VacationType type,
                                        double cancelCount) {
}
