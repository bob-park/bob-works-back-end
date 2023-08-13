package org.bobpark.maintenanceservice.domain.maintenance.cqrs.listner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.maintenanceservice.domain.maintenance.cqrs.command.CreateChatCommand;
import org.bobpark.maintenanceservice.domain.maintenance.cqrs.event.CreatedChatEvent;
import org.bobpark.maintenanceservice.domain.maintenance.cqrs.event.CreatedChatRoomEvent;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChat;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoom;
import org.bobpark.maintenanceservice.domain.maintenance.repository.CustomerChatRepository;
import org.bobpark.maintenanceservice.domain.maintenance.repository.CustomerChatRoomRepository;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class CustomerChatEventListener {

    private final CustomerChatRoomRepository chatRoomRepository;
    private final CustomerChatRepository chatRepository;

    @Async
    @Transactional
    @EventListener
    public void createdChatRoom(CreatedChatRoomEvent createdEvent) {

        CustomerChatRoom createdChatRoom =
            CustomerChatRoom.builder()
                .id(createdEvent.id())
                .customerId(createdEvent.customerId())
                .title(createdEvent.title())
                .description(createdEvent.description())
                .build();

        chatRoomRepository.save(createdChatRoom);

        log.debug("created customer chat room. (id={})", createdChatRoom.getId());
    }

    @Async
    @Transactional
    @EventListener
    public void createdChat(CreatedChatEvent createdEvent) {

        CustomerChatRoom chatRoom =
            chatRoomRepository.findById(createdEvent.roomId())
                .orElseThrow(() -> new NotFoundException(CustomerChatRoom.class, createdEvent.roomId()));

        CustomerChat createdChat =
            CustomerChat.builder()
                .id(createdEvent.id())
                .writerId(createdEvent.writerId())
                .contents(createdEvent.contents())
                .build();

        chatRoom.addChat(createdChat);

        chatRepository.save(createdChat);

        log.debug("created customer chat. (id={}, contents={})", createdChat.getId(), createdChat.getContents());

    }
}
