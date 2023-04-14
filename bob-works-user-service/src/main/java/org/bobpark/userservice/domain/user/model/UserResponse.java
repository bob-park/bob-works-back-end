package org.bobpark.userservice.domain.user.model;

import lombok.Builder;

import org.bobpark.userservice.domain.position.model.PositionResponse;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.entity.UserPosition;

@Builder
public record UserResponse(
    Long id,
    String userId,
    String email,
    String name,
    PositionResponse position
) {

    public static UserResponse toResponse(User user) {

        UserPosition position = user.getPosition();
        PositionResponse positionResponse = null;

        if (position != null) {
            positionResponse =
                PositionResponse.builder()
                    .id(position.getPosition().getId())
                    .name(position.getPosition().getName())
                    .build();
        }

        return UserResponse.builder()
            .id(user.getId())
            .userId(user.getUserId())
            .email(user.getEmail())
            .name(user.getName())
            .position(positionResponse)
            .build();
    }

}
