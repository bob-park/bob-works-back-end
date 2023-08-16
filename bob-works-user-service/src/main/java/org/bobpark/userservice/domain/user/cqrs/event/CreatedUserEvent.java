package org.bobpark.userservice.domain.user.cqrs.event;

import lombok.Builder;

@Builder
public record CreatedUserEvent(Long teamId,
                               Long roleId,
                               Long positionId,
                               String userId,
                               String encryptPassword,
                               String name,
                               String email,
                               String phone,
                               String description) {
}
