package org.bobpark.client.domain.maintenance.model;

import java.util.List;

import lombok.Builder;

import org.bobpark.client.domain.user.model.UserRequest;

@Builder
public record CreateChatRoomRequest(String name,
                                    String description,
                                    List<UserRequest> users) {
}
