package org.bobpark.authorizationservice.domain.user.model;

import lombok.Builder;

import org.bobpark.authorizationservice.domain.posotion.model.PositionResponse;
import org.bobpark.authorizationservice.domain.team.model.TeamResponse;

@Builder
public record UserResponse(Long id,
                           String userId,
                           String email,
                           String name,
                           String avatar,
                           PositionResponse position,
                           TeamResponse team) {

}
