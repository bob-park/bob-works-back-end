package org.bobpark.authorizationservice.domain.user.model;

public record UserVacationResponse(Long id,
                                   int year,
                                   VacationResponse general,
                                   VacationResponse alternative) {


}
