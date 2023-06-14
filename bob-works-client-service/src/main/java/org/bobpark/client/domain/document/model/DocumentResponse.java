package org.bobpark.client.domain.document.model;

import java.time.LocalDateTime;

public record DocumentResponse(Long id,
                               DocumentTypeResponse documentType,
                               Long writerId,
                               String status,
                               LocalDateTime createdDate,
                               String createdBy,
                               LocalDateTime lastModifiedDate,
                               String lastModifiedBy) {
}
