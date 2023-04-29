package org.bobpark.documentservice.domain.document.model.vacation;

import static org.bobpark.documentservice.domain.user.utils.UserUtils.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.entity.DocumentApproval;
import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.entity.vacation.VacationDocument;
import org.bobpark.documentservice.domain.document.model.DocumentTypeResponse;
import org.bobpark.documentservice.domain.document.model.approval.DocumentApprovalResponse;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;
import org.bobpark.documentservice.domain.document.type.VacationSubType;
import org.bobpark.documentservice.domain.document.type.VacationType;
import org.bobpark.documentservice.domain.user.model.UserResponse;
import org.bobpark.documentservice.domain.user.utils.UserUtils;

@Builder
public record VacationDocumentResponse(Long id,
                                       DocumentTypeName type,
                                       Long typeId,
                                       UserResponse writer,
                                       DocumentStatus status,
                                       LocalDateTime createdDate,
                                       String createdBy,
                                       LocalDateTime lastModifiedDate,
                                       String lastModifiedBy,
                                       VacationType vacationType,
                                       VacationSubType vacationSubType,
                                       LocalDate vacationDateFrom,
                                       LocalDate vacationDateTo,
                                       Double daysCount,
                                       String reason,
                                       List<DocumentApprovalResponse> approvals) {

    public static VacationDocumentResponse toResponse(VacationDocument vacationDocument, List<UserResponse> users) {
        return VacationDocumentResponse.builder()
            .id(vacationDocument.getId())
            .type(vacationDocument.getType())
            .typeId(vacationDocument.getId())
            .writer(findByUser(users, vacationDocument.getWriterId()))
            .status(vacationDocument.getStatus())
            .createdDate(vacationDocument.getCreatedDate())
            .createdBy(vacationDocument.getCreatedBy())
            .lastModifiedDate(vacationDocument.getLastModifiedDate())
            .lastModifiedBy(vacationDocument.getLastModifiedBy())
            .vacationType(vacationDocument.getVacationType())
            .vacationSubType(vacationDocument.getVacationSubType())
            .vacationDateFrom(vacationDocument.getVacationDateFrom())
            .vacationDateTo(vacationDocument.getVacationDateTo())
            .daysCount(vacationDocument.getDaysCount())
            .reason(vacationDocument.getReason())
            .approvals(
                vacationDocument.getApprovals().stream()
                    .map(DocumentApprovalResponse::toResponse)
                    .toList())
            .build();
    }
}
