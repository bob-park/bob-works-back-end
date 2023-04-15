package org.bobpark.userservice.domain.user.model.vacation;

import lombok.Builder;

import org.bobpark.userservice.domain.user.type.VacationType;

@Builder
public record CancelUserVacationRequest(VacationType type,
                                        double cancelCount) {
}
