package org.bobpark.maintenanceservice.domain.maintenance.model;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record CustomerChatResponse(String id,
                                   CustomerChatRoomResponse room,
                                   Long writerId,
                                   String contents,
                                   Boolean isRead,
                                   LocalDateTime createdDate,
                                   String createdBy,
                                   LocalDateTime lastModifiedDate,
                                   String lastModifiedBy) {
}
