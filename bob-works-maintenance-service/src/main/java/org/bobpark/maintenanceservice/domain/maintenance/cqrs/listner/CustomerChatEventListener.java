package org.bobpark.maintenanceservice.domain.maintenance.cqrs.listner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.maintenanceservice.common.utils.user.UserProvider;
import org.bobpark.maintenanceservice.domain.maintenance.cqrs.event.CreatedChatEvent;
import org.bobpark.maintenanceservice.domain.maintenance.cqrs.event.CreatedChatRoomEvent;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChat;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatId;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoom;
import org.bobpark.maintenanceservice.domain.maintenance.repository.CustomerChatRepository;
import org.bobpark.maintenanceservice.domain.maintenance.repository.CustomerChatRoomRepository;
import org.bobpark.maintenanceservice.domain.notification.provider.NotificationProvider;
import org.bobpark.maintenanceservice.domain.notification.provider.NotificationSendMessage;
import org.bobpark.maintenanceservice.domain.user.model.UserResponse;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class CustomerChatEventListener {

    // TODO 관리자 이거 나중에 바꿔야할 듯
    private static final long ADMIN_ID = 11;

    private final NotificationProvider notificationProvider;

    private final CustomerChatRoomRepository chatRoomRepository;
    private final CustomerChatRepository chatRepository;

    // @Async
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

    // @Async
    @Transactional
    @EventListener
    public void createdChat(CreatedChatEvent createdEvent) {

        CustomerChatRoom chatRoom =
            chatRoomRepository.findById(createdEvent.roomId())
                .orElseThrow(() -> new NotFoundException(CustomerChatRoom.class, createdEvent.roomId()));

        UserResponse user = UserProvider.getInstance().getUser();

        CustomerChat createdChat =
            CustomerChat.builder()
                .id(createdEvent.id())
                .writerId(user.id())
                .contents(createdEvent.contents())
                .build();

        chatRoom.addChat(createdChat);

        chatRepository.save(createdChat);

        log.debug("created customer chat. (id={}, contents={})", createdChat.getId(), createdChat.getContents());

        // admin
        CustomerChat createdMaintenanceChat =
            CustomerChat.builder()
                .id(new CustomerChatId())
                .writerId(ADMIN_ID)
                .contents("접수되었습니다.")
                .build();

        chatRoom.addChat(createdMaintenanceChat);

        chatRepository.save(createdMaintenanceChat);

        notificationProvider.sendMessage(
            new NotificationSendMessage(String.format("%s (%s)", user.userId(), user.name()),
                createdChat.getContents()));
    }
}
