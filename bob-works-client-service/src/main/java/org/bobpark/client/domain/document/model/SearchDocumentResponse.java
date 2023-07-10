package org.bobpark.client.domain.document.model;

import java.time.LocalDateTime;

import lombok.Builder;

import org.bobpark.client.domain.user.model.UserResponse;

@Builder
public record SearchDocumentResponse(Long id,
                                     DocumentTypeResponse documentType,
                                     UserResponse writer,
                                     String status,
                                     LocalDateTime createdDate,
                                     String createdBy,
                                     LocalDateTime lastModifiedDate,
                                     String lastModifiedBy) {
}
