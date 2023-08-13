package org.bobpark.maintenanceservice.domain.notification.provider.slack.command;

import lombok.Builder;

import org.bobpark.maintenanceservice.domain.notification.command.SendNotificationCommand;

@Builder
public record SlackSendCommand(String userId,
                               String contents)
    implements SendNotificationCommand {

}
