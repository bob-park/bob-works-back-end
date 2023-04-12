package org.bobpark.documentservice.domain.user.model;

import lombok.Builder;

import org.bobpark.documentservice.domain.position.entity.Position;
import org.bobpark.documentservice.domain.position.model.PositionResponse;
import org.bobpark.documentservice.domain.user.entity.User;
import org.bobpark.documentservice.domain.user.entity.UserPosition;

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
