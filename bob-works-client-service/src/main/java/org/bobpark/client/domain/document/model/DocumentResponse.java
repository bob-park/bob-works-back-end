package org.bobpark.client.domain.document.model;

import java.time.LocalDateTime;

import lombok.Builder;

import org.bobpark.client.domain.user.model.UserResponse;

@Builder(toBuilder = true)
public record DocumentResponse(Long id,
                               DocumentTypeResponse documentType,
                               Long writerId,
                               UserResponse writer,
                               String status,
                               LocalDateTime createdDate,
                               String createdBy,
                               LocalDateTime lastModifiedDate,
                               String lastModifiedBy) {
}
