package org.bobpark.client.domain.chat.model;

import lombok.Builder;

import org.bobpark.client.domain.user.model.UserResponse;

@Builder
public record ChatRoomUserResponse(String id,
                                   UserResponse user) {
}
