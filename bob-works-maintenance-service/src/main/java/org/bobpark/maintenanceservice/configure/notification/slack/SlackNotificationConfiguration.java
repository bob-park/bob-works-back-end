package org.bobpark.maintenanceservice.configure.notification.slack;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.bobpark.maintenanceservice.configure.notification.properties.NotificationProperties;
import org.bobpark.maintenanceservice.domain.notification.provider.NotificationProvider;
import org.bobpark.maintenanceservice.domain.notification.provider.slack.SlackNotificationProvider;

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
    public NotificationProvider slackNotificationProvider(ObjectMapper objectMapper) {
        return new SlackNotificationProvider(properties, properties.getSlack().getWebHookUrl(), restTemplate(),
            objectMapper);
    }
}
