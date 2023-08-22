package org.bobpark.userservice.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.bobpark.userservice.domain.user.cqrs.aggregate.UserAggregate;
import org.bobpark.userservice.domain.user.cqrs.command.CreateUserCommand;
import org.bobpark.userservice.domain.user.cqrs.command.CreateUserVacationCommand;
import org.bobpark.userservice.domain.user.model.CreateUserRequest;
import org.bobpark.userservice.domain.user.model.UserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserCommandService {

    private final PasswordEncoder passwordEncoder;

    private final UserAggregate userAggregate;

    public UserResponse createUser(CreateUserRequest createRequest) {

        CreateUserCommand createUserCommand =
            CreateUserCommand.builder()
                .teamId(createRequest.teamId())
                .roleId(createRequest.roleId())
                .positionId(createRequest.positionId())
                .userId(createRequest.userId())
                .encryptPassword(passwordEncoder.encode(createRequest.password()))
                .name(createRequest.name())
                .email(createRequest.email())
                .phone(createRequest.phone())
                .description(createRequest.description())
                .employmentDate(createRequest.employmentDate())
                .vacation(
                    CreateUserVacationCommand.builder()
                        .generalTotalCount(createRequest.vacation().generalTotalCount())
                        .generalUsedCount(createRequest.vacation().generalTotalUsedCount())
                        .alternativeTotalCount(createRequest.vacation().alternativeTotalCount())
                        .alternativeUsedCount(createRequest.vacation().alternativeUsedCount())
                        .build())
                .build();

        return userAggregate.createUser(createUserCommand);

    }

}
