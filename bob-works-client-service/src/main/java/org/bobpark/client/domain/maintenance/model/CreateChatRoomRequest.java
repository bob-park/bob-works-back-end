package org.bobpark.client.domain.maintenance.model;

import lombok.Builder;

@Builder
public record CreateChatRoomRequest(String title,
                                    String description) {
}
