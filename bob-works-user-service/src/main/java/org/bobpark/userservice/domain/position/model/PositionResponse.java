package org.bobpark.userservice.domain.position.model;

import lombok.Builder;

import org.bobpark.userservice.domain.position.entity.Position;

@Builder
public record PositionResponse(Long id,
                               String name
) {

    public static PositionResponse toResponse(Position position) {
        return PositionResponse.builder()
            .id(position.getId())
            .name(position.getName())
            .build();
    }
}
