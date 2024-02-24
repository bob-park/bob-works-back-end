package org.bobpark.userservice.domain.user.producer.notification;

import org.bobpark.userservice.domain.user.entity.notification.NotificationType;

public interface UserNotificationProducer {

    void send(SendCommand sendCommand);

    default boolean isSupport(NotificationType type) {
        return false;
    }

}
