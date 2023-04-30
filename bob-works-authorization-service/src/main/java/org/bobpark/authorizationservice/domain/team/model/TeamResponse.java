package org.bobpark.authorizationservice.domain.team.model;

import java.time.LocalDateTime;

public record TeamResponse(Long id,
                           String name,
                           String description,
                           LocalDateTime createdDate,
                           String createdBy,
                           LocalDateTime lastModifiedDate,
                           String lastModifiedBy) {
}
