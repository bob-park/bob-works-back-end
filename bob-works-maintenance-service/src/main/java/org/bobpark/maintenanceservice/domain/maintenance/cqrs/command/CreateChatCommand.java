package org.bobpark.maintenanceservice.domain.maintenance.cqrs.command;

import lombok.Builder;

import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoomId;

@Builder
public record CreateChatCommand(CustomerChatRoomId roomId,
                                String writer,
                                String contents) {
}
