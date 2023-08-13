package org.bobpark.maintenanceservice.domain.maintenance.service;

import static com.google.common.base.Preconditions.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import org.apache.commons.lang.StringUtils;

import org.bobpark.maintenanceservice.common.utils.user.UserProvider;
import org.bobpark.maintenanceservice.domain.maintenance.cqrs.aggregate.CustomerChatAggregate;
import org.bobpark.maintenanceservice.domain.maintenance.cqrs.command.CreateChatRoomCommand;
import org.bobpark.maintenanceservice.domain.maintenance.model.CreateChatRoomRequest;
import org.bobpark.maintenanceservice.domain.maintenance.model.CustomerChatRoomResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerChatRoomCommandService {

    private final CustomerChatAggregate chatAggregate;

    public CustomerChatRoomResponse createRoom(CreateChatRoomRequest createRequest) {

        checkArgument(StringUtils.isNotBlank(createRequest.title()), "title must be provided.");

        long customerId = UserProvider.getInstance().getUserId();

        return chatAggregate.handleCreateChatRoom(
            CreateChatRoomCommand.builder()
                .customerId(customerId)
                .title(createRequest.title())
                .description(createRequest.description())
                .build());
    }

}
