package org.bobpark.maintenanceservice.domain.notification.provider.slack.message;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SlackMessageBlock {

    private final String type;
    private final SlackMessageBlockText text;

    @Builder
    private SlackMessageBlock(String type, SlackMessageBlockText text) {
        this.type = type;
        this.text = text;
    }
}
