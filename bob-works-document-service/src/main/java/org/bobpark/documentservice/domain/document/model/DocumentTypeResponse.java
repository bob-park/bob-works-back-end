package org.bobpark.documentservice.domain.document.model;

import java.time.LocalDateTime;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.type.DocumentTypeName;

@Builder
public record DocumentTypeResponse(
    Long id,
    DocumentTypeName type,
    String name,
    String description,
    LocalDateTime createdDate,
    String createdBy,
    LocalDateTime lastModifiedDate,
    String lastModifiedBy
) {
}
