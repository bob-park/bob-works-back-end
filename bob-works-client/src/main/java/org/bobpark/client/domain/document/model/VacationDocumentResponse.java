package org.bobpark.client.domain.document.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.bobpark.client.domain.user.model.UserResponse;

public record VacationDocumentResponse(Long id,
                                       String type,
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
                                       String reason) {
}
