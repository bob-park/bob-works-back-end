package org.bobpark.userservice.domain.user.producer.notification.slack;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import org.bobpark.userservice.domain.user.entity.notification.NotificationType;
import org.bobpark.userservice.domain.user.producer.notification.SendCommand;
import org.bobpark.userservice.domain.user.producer.notification.UserNotificationProducer;

@Slf4j
@RequiredArgsConstructor
public class SlackNotificationProducer implements UserNotificationProducer {

    private final RestTemplate restTemplate;

    @Override
    public void send(SendCommand sendCommand) {

        RequestEntity<SlackMessage> requestEntity =
            RequestEntity.post(sendCommand.hookUrl())
                .body(new SlackMessage(sendCommand.message()));

        restTemplate.exchange(requestEntity, String.class);

    }

    @Override
    public boolean isSupport(NotificationType type) {
        return type == NotificationType.SLACK;
    }

}
