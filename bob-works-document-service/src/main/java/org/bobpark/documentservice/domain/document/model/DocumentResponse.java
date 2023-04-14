package org.bobpark.documentservice.domain.document.model;

import java.time.LocalDateTime;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;
import org.bobpark.documentservice.domain.user.model.UserResponse;

@Builder
public record DocumentResponse(Long id,
                               DocumentTypeName type,
                               UserResponse writer,
                               DocumentStatus status,
                               LocalDateTime createdDate,
                               String createdBy,
                               LocalDateTime lastModifiedDate,
                               String lastModifiedBy) {

    public static DocumentResponse toResponse(Document vacationDocument) {
        return DocumentResponse.builder()
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
