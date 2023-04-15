package org.bobpark.userservice.domain.user.model.vacation;

import lombok.Builder;

import org.bobpark.userservice.domain.user.entity.UserVacation;

@Builder
public record UserVacationResponse(Long id,
                                   int year,
                                   VacationResponse general,
                                   VacationResponse alternative) {

    public static UserVacationResponse toResponse(UserVacation userVacation) {
        return UserVacationResponse.builder()
            .id(userVacation.getId())
            .year(userVacation.getYear())
            .general(VacationResponse.toResponse(userVacation.getGeneralVacations()))
            .alternative(VacationResponse.toResponse(userVacation.getAlternativeVacations()))
            .build();
    }

}
