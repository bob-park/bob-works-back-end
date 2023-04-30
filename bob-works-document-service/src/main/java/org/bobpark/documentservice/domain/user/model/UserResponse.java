package org.bobpark.documentservice.domain.user.model;

import org.bobpark.documentservice.domain.position.model.PositionResponse;
import org.bobpark.documentservice.domain.team.model.TeamResponse;
import org.bobpark.documentservice.domain.user.model.vacation.UserVacationResponse;

public record UserResponse(Long id,
                           String userId,
                           String email,
                           String name,
                           TeamResponse team,
                           PositionResponse position,
                           UserVacationResponse nowVacation) {
}
