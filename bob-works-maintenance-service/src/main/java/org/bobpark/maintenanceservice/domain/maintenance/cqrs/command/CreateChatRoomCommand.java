package org.bobpark.maintenanceservice.domain.maintenance.cqrs.command;

import lombok.Builder;

@Builder
public record CreateChatRoomCommand(Long customerId,
                                    String title,
                                    String description) {
}
