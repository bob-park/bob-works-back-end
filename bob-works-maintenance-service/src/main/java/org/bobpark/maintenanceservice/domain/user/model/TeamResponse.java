package org.bobpark.maintenanceservice.domain.user.model;

import java.time.LocalDateTime;

public record TeamResponse(Long id,
                           String name,
                           String description,
                           LocalDateTime createdDate,
                           String createdBy,
                           LocalDateTime lastModifiedDate,
                           String lastModifiedBy) {

}
