package org.bobpark.userservice.domain.user.cqrs.event;

import java.time.LocalDate;

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
                               String description,
                               LocalDate employmentDate,
                               CreatedUserVacationEvent vacation) {

    public CreatedUserEvent {
        if (vacation == null) {
            vacation = new CreatedUserVacationEvent(0.0, 0.0, 0.0, 0.0);
        }
    }
}
