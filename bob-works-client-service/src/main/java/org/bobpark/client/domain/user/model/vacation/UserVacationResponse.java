package org.bobpark.client.domain.user.model.vacation;

import lombok.Builder;

@Builder
public record UserVacationResponse(Long id,
                                   int year,
                                   VacationResponse general,
                                   VacationResponse alternative) {

}
