package org.bobpark.maintenanceservice.domain.maintenance.cqrs.command;

import lombok.Builder;

@Builder
public record CreateChatCommand(String roomId,
                                Long writerId,
                                String contents) {
}
