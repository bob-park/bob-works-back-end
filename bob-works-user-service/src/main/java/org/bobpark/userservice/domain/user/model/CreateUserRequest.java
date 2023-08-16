package org.bobpark.userservice.domain.user.model;

import java.time.LocalDate;

public record CreateUserRequest(Long teamId,
                                Long roleId,
                                Long positionId,
                                String userId,
                                String password,
                                String name,
                                String email,
                                String phone,
                                String description,
                                LocalDate employmentDate,
                                CreateUserVacationRequest vacation) {
}
