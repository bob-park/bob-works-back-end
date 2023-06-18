package org.bobpark.userservice.domain.user.model.vacation;

import java.time.LocalDate;

import lombok.Builder;

import org.bobpark.userservice.domain.user.entity.vacation.UserAlternativeVacation;
import org.bobpark.userservice.domain.user.model.UserResponse;

@Builder
public record UserAlternativeVacationResponse(Long id,
                                              UserResponse user,
                                              LocalDate effectiveDate,
                                              Double effectiveCount,
                                              String effectiveReason,
                                              Double usedCount) {

    public static UserAlternativeVacationResponse toResponse(UserAlternativeVacation entity) {
        return UserAlternativeVacationResponse.builder()
            .id(entity.getId())
            // .user(UserResponse.toResponse(entity.getUser()))
            .effectiveDate(entity.getEffectiveDate())
            .effectiveCount(entity.getEffectiveCount())
            .effectiveReason(entity.getEffectiveReason())
            .usedCount(entity.getUsedCount())
            .build();
    }
}
