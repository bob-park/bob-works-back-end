package org.bobpark.maintenanceservice.configure.notification.slack;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import org.bobpark.maintenanceservice.configure.notification.properties.NotificationProperties;
import org.bobpark.maintenanceservice.domain.notification.provider.NotificationProvider;
import org.bobpark.maintenanceservice.domain.notification.provider.slack.SlackNotificationProvider;
import org.bobpark.maintenanceservice.domain.notification.provider.slack.command.SlackSendCommand;

@RequiredArgsConstructor
@ConditionalOnProperty(name = "noti.type", havingValue = "slack")
@Configuration
public class SlackNotificationConfiguration {
    private final NotificationProperties properties;

    @Bean("slackNotificationProviderRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public NotificationProvider<SlackSendCommand> slackNotificationProvider() {
        return new SlackNotificationProvider(properties, properties.getSlack().getWebHookUrl(), restTemplate());
    }
}
