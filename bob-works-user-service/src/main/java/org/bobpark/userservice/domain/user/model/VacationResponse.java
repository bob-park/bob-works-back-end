package org.bobpark.userservice.domain.user.model;

import lombok.Builder;

import org.bobpark.userservice.domain.user.entity.Vacation;

@Builder
public record VacationResponse(double totalCount,
                               double usedCount) {

    public static VacationResponse toResponse(Vacation vacation) {
        return VacationResponse.builder()
            .totalCount(vacation.getTotalCount())
            .usedCount(vacation.getUsedCount())
            .build();
    }
}
