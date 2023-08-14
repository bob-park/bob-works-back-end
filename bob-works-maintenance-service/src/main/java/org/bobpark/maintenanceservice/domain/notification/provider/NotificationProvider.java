package org.bobpark.maintenanceservice.domain.notification.provider;

public interface NotificationProvider {

    void sendMessage(NotificationSendMessage sendMessage);

}
