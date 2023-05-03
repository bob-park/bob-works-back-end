package org.bobpark.client.domain.document.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.bobpark.client.domain.user.model.UserResponse;

public record VacationDocumentResponse(Long id,
                                       String type,
                                       Long typeId,
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
                                       List<DocumentApprovalResponse> approvals) {
}
