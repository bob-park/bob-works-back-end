package org.bobpark.maintenanceservice.domain.maintenance.cqrs.command;

import lombok.Builder;

import org.bobpark.bobsonclient.event.client.model.EventCommand;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatId;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoomId;

@Builder
public record CreateChatCommand(String id,
                                String roomId,
                                Long writerId,
                                String contents)
    implements EventCommand {
}
