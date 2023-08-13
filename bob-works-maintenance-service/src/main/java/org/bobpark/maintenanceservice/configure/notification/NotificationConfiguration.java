package org.bobpark.maintenanceservice.configure.notification;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import org.bobpark.maintenanceservice.configure.notification.properties.NotificationProperties;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(NotificationProperties.class)
public class NotificationConfiguration {

    private final NotificationProperties properties;


}
