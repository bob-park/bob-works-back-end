package org.bobpark.maintenanceservice.domain.notification.provider;

import org.bobpark.maintenanceservice.domain.notification.command.SendNotificationCommand;

public interface NotificationProvider<C extends SendNotificationCommand> {

    void sendMessage(C sendCommand);

}
