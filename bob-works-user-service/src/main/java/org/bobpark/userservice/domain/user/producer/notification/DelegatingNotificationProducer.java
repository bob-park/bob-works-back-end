package org.bobpark.userservice.domain.user.producer.notification;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.bobpark.userservice.domain.user.entity.notification.NotificationType;

@Slf4j
public class DelegatingNotificationProducer {

    private final List<UserNotificationProducer> producers = new ArrayList<>();

    public void send(NotificationType type, SendCommand sendCommand) {
        for (UserNotificationProducer producer : producers) {
            if (producer.isSupport(type)) {
                producer.send(sendCommand);
            }
        }
    }

    public void addProducer(UserNotificationProducer producer) {
        producers.add(producer);

        log.debug("added user notification producer. ({})", producer.getClass().getSimpleName());
    }

}
