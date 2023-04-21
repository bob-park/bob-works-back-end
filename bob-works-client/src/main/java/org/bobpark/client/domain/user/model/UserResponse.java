package org.bobpark.client.domain.user.model;

import org.bobpark.client.domain.position.model.PositionResponse;
import org.bobpark.client.domain.user.model.vacation.UserVacationResponse;

public record UserResponse(Long id,
                           String userId,
                           String email,
                           String name,
                           PositionResponse position,
                           UserVacationResponse nowVacation) {
}
