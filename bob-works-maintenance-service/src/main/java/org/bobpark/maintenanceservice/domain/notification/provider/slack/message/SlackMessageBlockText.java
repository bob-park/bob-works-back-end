package org.bobpark.maintenanceservice.domain.notification.provider.slack.message;

import lombok.Builder;

@Builder
public record SlackMessageBlockText(String type,
                                    String text,
                                    boolean emoji) {
}
