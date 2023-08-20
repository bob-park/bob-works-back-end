package org.bobpark.maintenanceservice.domain.maintenance.cqrs.command;

import lombok.Builder;

@Builder
public record CreateChatCommand(String id,
                                String roomId,
                                Long writerId,
                                String contents) {
}
