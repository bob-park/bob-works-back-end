package org.bobpark.maintenanceservice.domain.notification.provider.slack.message;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SlackMessageBlock {

    private final String type;
    private final SlackMessageBlockText text;
    private final List<SlackMessageBlockText> elements;

    @Builder
    private SlackMessageBlock(String type, SlackMessageBlockText text, List<SlackMessageBlockText> elements) {
        this.type = type;
        this.text = text;
        this.elements = elements;
    }
}
