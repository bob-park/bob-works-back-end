package org.bobpark.client.domain.document.model;

import java.time.LocalDateTime;

public record DocumentTypeResponse(Long id,
                                   String type,
                                   String name,
                                   LocalDateTime createdDate,
                                   String createdBy,
                                   LocalDateTime lastModifiedDate,
                                   String lastModifiedBy) {
}
