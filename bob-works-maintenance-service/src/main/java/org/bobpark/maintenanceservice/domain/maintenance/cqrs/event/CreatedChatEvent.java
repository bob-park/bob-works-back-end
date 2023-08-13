package org.bobpark.maintenanceservice.domain.maintenance.cqrs.event;

import lombok.Builder;

import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatId;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoomId;

@Builder
public record CreatedChatEvent(CustomerChatId id,
                               CustomerChatRoomId roomId,
                               String writer,
                               String contents) {
}
