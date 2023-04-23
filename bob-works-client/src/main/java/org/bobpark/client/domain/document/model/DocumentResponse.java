package org.bobpark.client.domain.document.model;

import java.time.LocalDateTime;

import org.bobpark.client.domain.user.model.UserResponse;

public record DocumentResponse(Long id,
                               String type,
                               UserResponse writer,
                               String status,
                               LocalDateTime createdDate,
                               String createdBy,
                               LocalDateTime lastModifiedDate,
                               String lastModifiedBy) {
}
