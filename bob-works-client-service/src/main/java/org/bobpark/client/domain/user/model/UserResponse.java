package org.bobpark.client.domain.user.model;

import lombok.Builder;

import org.bobpark.client.domain.maintenance.model.CustomerChatRoomResponse;
import org.bobpark.client.domain.position.model.PositionResponse;
import org.bobpark.client.domain.team.model.TeamResponse;
import org.bobpark.client.domain.user.model.vacation.UserVacationResponse;

@Builder(toBuilder = true)
public record UserResponse(Long id,
                           String userId,
                           String email,
                           String name,
                           String role,
                           String avatar,
                           PositionResponse position,
                           UserVacationResponse nowVacation,
                           TeamResponse team,
                           CustomerChatRoomResponse chatRoom) {
}
