package org.bobpark.userservice.domain.user.model.vacation;

import java.util.Collections;
import java.util.List;

import lombok.Builder;

import org.bobpark.userservice.domain.user.type.VacationType;

@Builder
public record UseUserVacationRequest(VacationType type,
                                     List<Long> useAlternativeVacationIds,
                                     double useCount) {

    public UseUserVacationRequest {
        if (useAlternativeVacationIds == null) {
            useAlternativeVacationIds = Collections.emptyList();
        }
    }
}
