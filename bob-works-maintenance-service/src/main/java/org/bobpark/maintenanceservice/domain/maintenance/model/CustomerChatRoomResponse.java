package org.bobpark.maintenanceservice.domain.maintenance.model;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record CustomerChatRoomResponse(String id,
                                       Long customerId,
                                       Long managerId,
                                       String title,
                                       String description,
                                       LocalDateTime createdDate,
                                       String createdBy,
                                       LocalDateTime lastModifiedDate,
                                       String lastModifiedBy) {
}
