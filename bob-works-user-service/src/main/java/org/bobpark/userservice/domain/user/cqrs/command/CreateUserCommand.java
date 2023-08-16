package org.bobpark.userservice.domain.user.cqrs.command;

import lombok.Builder;

@Builder
public record CreateUserCommand(Long teamId,
                                Long roleId,
                                Long positionId,
                                String userId,
                                String encryptPassword,
                                String name,
                                String email,
                                String phone,
                                String description) {
}
