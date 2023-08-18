package org.bobpark.maintenanceservice.domain.notification.provider.slack.message;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SlackMessage {

    private List<SlackMessageBlock> blocks;
    private List<SlackMessageAttachment> attachments;

    public void addBlock(SlackMessageBlock block) {
        if (blocks == null) {
            blocks = new ArrayList<>();
        }

        blocks.add(block);
    }

    public void addAttachment(SlackMessageAttachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }

        attachments.add(attachment);
    }
}
