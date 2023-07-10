package org.bobpark.documentservice.domain.document.model;

import java.time.LocalDateTime;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;

@Builder
public record DocumentResponse(Long id,
                               DocumentTypeResponse documentType,
                               Long writerId,
                               DocumentStatus status,
                               LocalDateTime createdDate,
                               String createdBy,
                               LocalDateTime lastModifiedDate,
                               String lastModifiedBy) {

    public static DocumentResponse toResponse(Document document) {
        return DocumentResponse.builder()
            .id(document.getId())
            .documentType(DocumentTypeResponse.toResponse(document.getDocumentType()))
            .writerId( document.getWriterId())
            .status(document.getStatus())
            .createdDate(document.getCreatedDate())
            .createdBy(document.getCreatedBy())
            .lastModifiedDate(document.getLastModifiedDate())
            .lastModifiedBy(document.getLastModifiedBy())
            .build();
    }

}
