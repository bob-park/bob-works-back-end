package org.bobpark.maintenanceservice.domain.maintenance.cqrs.event;

import lombok.Builder;

import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoomId;

@Builder
public record CreatedChatRoomEvent(CustomerChatRoomId id,
                                   Long customerId,
                                   String title,
                                   String description) {
}
