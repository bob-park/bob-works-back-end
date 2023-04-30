package org.bobpark.userservice.domain.team.model;

import java.time.LocalDateTime;

import lombok.Builder;

import org.bobpark.userservice.domain.team.entity.Team;

@Builder
public record TeamResponse(Long id,
                           String name,
                           String description,
                           LocalDateTime createdDate,
                           String createdBy,
                           LocalDateTime lastModifiedDate,
                           String lastModifiedBy) {

    public static TeamResponse toResponse(Team team) {
        return TeamResponse.builder()
            .id(team.getId())
            .name(team.getName())
            .description(team.getDescription())
            .createdDate(team.getCreatedDate())
            .createdBy(team.getCreatedBy())
            .lastModifiedDate(team.getLastModifiedDate())
            .lastModifiedBy(team.getLastModifiedBy())
            .build();
    }
}
