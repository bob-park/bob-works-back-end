package org.bobpark.maintenanceservice.domain.notification.provider.slack.message;

import java.util.List;

public record SlackMessageAttachment(String color,
                                     List<SlackMessageBlock> blocks) {
}
