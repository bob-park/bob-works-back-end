package org.bobpark.client.domain.document.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

import org.bobpark.client.domain.user.model.UserResponse;
import org.bobpark.client.domain.user.model.vacation.UserAlternativeVacationResponse;

@Builder(toBuilder = true)
public record UsageVacationResponse(Long id,
                                    String type,
                                    Long typeId,
                                    Long writerId,
                                    UserResponse writer,
                                    String status,
                                    LocalDateTime createdDate,
                                    String createdBy,
                                    LocalDateTime lastModifiedDate,
                                    String lastModifiedBy,
                                    String vacationType,
                                    String vacationSubType,
                                    LocalDate vacationDateFrom,
                                    LocalDate vacationDateTo,
                                    Double daysCount,
                                    String reason,
                                    List<UserAlternativeVacationResponse> alternativeVacations) {
}
