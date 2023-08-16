package org.bobpark.userservice.domain.user.cqrs.aggregate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import org.bobpark.userservice.domain.user.cqrs.command.CreateUserCommand;
import org.bobpark.userservice.domain.user.cqrs.event.CreatedUserEvent;
import org.bobpark.userservice.domain.user.cqrs.event.CreatedUserVacationEvent;
import org.bobpark.userservice.domain.user.model.UserResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserAggregate {

    private final ApplicationEventPublisher eventPublisher;

    public UserResponse createUser(CreateUserCommand createCommand) {

        CreatedUserEvent createdUserEvent =
            CreatedUserEvent.builder()
                .teamId(createCommand.teamId())
                .roleId(createCommand.roleId())
                .positionId(createCommand.positionId())
                .userId(createCommand.userId())
                .encryptPassword(createCommand.encryptPassword())
                .name(createCommand.name())
                .email(createCommand.email())
                .phone(createCommand.phone())
                .description(createCommand.description())
                .vacation(
                    CreatedUserVacationEvent.builder()
                        .generalTotalCount(createCommand.vacation().generalTotalCount())
                        .alternativeTotalCount(createCommand.vacation().alternativeTotalCount())
                        .build())
                .build();

        eventPublisher.publishEvent(createdUserEvent);

        return UserResponse.builder()
            .userId(createCommand.userId())
            .name(createCommand.name())
            .build();

    }
}
