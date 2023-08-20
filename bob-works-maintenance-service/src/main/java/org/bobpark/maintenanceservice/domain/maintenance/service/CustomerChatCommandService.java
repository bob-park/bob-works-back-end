package org.bobpark.maintenanceservice.domain.maintenance.service;

import static com.google.common.base.Preconditions.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;

import org.bobpark.maintenanceservice.common.utils.user.UserProvider;
import org.bobpark.maintenanceservice.domain.maintenance.cqrs.aggregate.CustomerChatAggregate;
import org.bobpark.maintenanceservice.domain.maintenance.cqrs.command.CreateChatCommand;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatId;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoomId;
import org.bobpark.maintenanceservice.domain.maintenance.model.CreateChatRequest;
import org.bobpark.maintenanceservice.domain.maintenance.model.CustomerChatResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerChatCommandService {

    private final CustomerChatAggregate chatAggregate;

    public CustomerChatResponse createChat(CustomerChatRoomId roomId, CreateChatRequest createRequest) {

        checkArgument(StringUtils.isNotBlank(createRequest.contents()), "contents must be provided");

        long writerId = UserProvider.getInstance().getUserId();

        return chatAggregate.handleCreateChat(
            CreateChatCommand.builder()
                .id(new CustomerChatId().getId())
                .roomId(roomId.getId())
                .writerId(writerId)
                .contents(createRequest.contents())
                .build());
    }

}
