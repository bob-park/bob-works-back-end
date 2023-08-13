package org.bobpark.maintenanceservice.domain.maintenance.cqrs.aggregate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import org.bobpark.maintenanceservice.domain.maintenance.cqrs.command.CreateChatCommand;
import org.bobpark.maintenanceservice.domain.maintenance.cqrs.command.CreateChatRoomCommand;
import org.bobpark.maintenanceservice.domain.maintenance.cqrs.event.CreatedChatEvent;
import org.bobpark.maintenanceservice.domain.maintenance.cqrs.event.CreatedChatRoomEvent;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatId;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoomId;
import org.bobpark.maintenanceservice.domain.maintenance.model.CustomerChatResponse;
import org.bobpark.maintenanceservice.domain.maintenance.model.CustomerChatRoomResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerChatAggregate {

    private final ApplicationEventPublisher eventPublisher;

    public CustomerChatRoomResponse handleCreateChatRoom(CreateChatRoomCommand creatCommand) {

        CustomerChatRoomId id = new CustomerChatRoomId();

        eventPublisher.publishEvent(
            CreatedChatRoomEvent.builder()
                .id(id)
                .customerId(creatCommand.customerId())
                .title(creatCommand.title())
                .description(creatCommand.description())
                .build());

        return CustomerChatRoomResponse.builder()
            .id(id.getId())
            .title(creatCommand.title())
            .description(creatCommand.description())
            .build();
    }

    public CustomerChatResponse handleCreateChat(CreateChatCommand createCommand) {

        CustomerChatId id = new CustomerChatId();
        CustomerChatRoomId roomId = new CustomerChatRoomId(createCommand.roomId());

        eventPublisher.publishEvent(
            CreatedChatEvent.builder()
                .id(id)
                .roomId(roomId)
                .writerId(createCommand.writerId())
                .contents(createCommand.contents())
                .build());

        return CustomerChatResponse.builder()
            .id(id.getId())
            .writerId(createCommand.writerId())
            .contents(createCommand.contents())
            .build();
    }

}
