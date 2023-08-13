package org.bobpark.maintenanceservice.configure.notification.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import org.bobpark.maintenanceservice.domain.notification.type.NotificationType;

@ToString
@Getter
@Setter
@ConfigurationProperties("noti")
public class NotificationProperties {

    private boolean enabled;
    private NotificationType type;

    @NestedConfigurationProperty
    private SlackProperties slack;

    public NotificationProperties() {
        this.enabled = false;
    }
}
