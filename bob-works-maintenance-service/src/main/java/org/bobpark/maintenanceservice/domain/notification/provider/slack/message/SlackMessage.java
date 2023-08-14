package org.bobpark.maintenanceservice.domain.notification.provider.slack.message;

import static org.apache.commons.lang3.ObjectUtils.*;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SlackMessage {

    private final List<SlackMessageBlock> blocks;

    public SlackMessage(List<SlackMessageBlock> blocks) {
        this.blocks = defaultIfNull(blocks, Collections.emptyList());
    }
}
