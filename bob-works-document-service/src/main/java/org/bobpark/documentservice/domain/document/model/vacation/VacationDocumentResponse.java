package org.bobpark.documentservice.domain.document.model.vacation;

import java.time.LocalDateTime;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.entity.vacation.VacationDocument;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;
import org.bobpark.documentservice.domain.user.model.UserResponse;

@Builder
public record VacationDocumentResponse(Long id,
                                       DocumentTypeName type,
                                       UserResponse writer,
                                       DocumentStatus status,
                                       LocalDateTime createdDate,
                                       String createdBy,
                                       LocalDateTime lastModifiedDate,
                                       String lastModifiedBy
) {

    public static VacationDocumentResponse toResponse(VacationDocument vacationDocument) {
        return VacationDocumentResponse.builder()
            .id(vacationDocument.getId())
            .type(vacationDocument.getType())
            .writer(UserResponse.toResponse(vacationDocument.getWriter()))
            .status(vacationDocument.getStatus())
            .createdDate(vacationDocument.getCreatedDate())
            .createdBy(vacationDocument.getCreatedBy())
            .lastModifiedDate(vacationDocument.getLastModifiedDate())
            .lastModifiedBy(vacationDocument.getLastModifiedBy())
            .build();
    }
}
