package org.bobpark.client.domain.chat.model;

import java.util.List;

import lombok.Builder;

import org.bobpark.client.domain.user.model.UserRequest;

@Builder
public record AddChatRoomUserRequest(List<UserRequest> users) {
}
