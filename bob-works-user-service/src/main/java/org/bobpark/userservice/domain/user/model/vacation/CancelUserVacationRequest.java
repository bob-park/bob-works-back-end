package org.bobpark.userservice.domain.user.model.vacation;

import java.util.Collections;
import java.util.List;

import lombok.Builder;

import org.bobpark.userservice.domain.user.type.VacationType;

@Builder
public record CancelUserVacationRequest(VacationType type,
                                        List<Long> cancelAlternativeVacationIds,
                                        double cancelCount) {
    public CancelUserVacationRequest {
        if (cancelAlternativeVacationIds == null) {
            cancelAlternativeVacationIds = Collections.emptyList();
        }
    }
}
