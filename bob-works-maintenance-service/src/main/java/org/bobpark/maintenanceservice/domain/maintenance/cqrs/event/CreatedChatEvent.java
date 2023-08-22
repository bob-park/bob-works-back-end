package org.bobpark.maintenanceservice.domain.maintenance.cqrs.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CreatedChatEvent {
    private String id;
    private String roomId;
    private Long writerId;
    private String contents;
}
