package org.bobpark.documentservice.common.utils.notification;

import org.springframework.beans.factory.annotation.Autowired;

import org.bobpark.documentservice.domain.user.feign.client.UserClient;
import org.bobpark.documentservice.domain.user.model.notification.SendUserNotificationV1Request;

public final class NotificationUtils {

    private static NotificationUtils instance;

    private UserClient userClient;

    public static NotificationUtils getInstance() {
        if (instance == null) {
            instance = new NotificationUtils();
        }

        return instance;
    }

    public void sendMessage(long userId, String message) {
        userClient.sendMessage(userId, new SendUserNotificationV1Request(message));
    }

    @Autowired
    public void setClient(UserClient userClient) {
        this.userClient = userClient;
    }
}
