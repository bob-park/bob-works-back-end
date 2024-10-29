package org.bobpark.maintenanceservice.domain.maintenance.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.maintenanceservice.common.utils.user.UserProvider;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoom;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoomId;
import org.bobpark.maintenanceservice.domain.maintenance.model.CustomerChatRoomResponse;
import org.bobpark.maintenanceservice.domain.maintenance.repository.CustomerChatRoomRepository;
import org.bobpark.maintenanceservice.domain.user.model.UserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CustomerChatRoomQueryService {

    private final CustomerChatRoomRepository chatRoomRepository;

    public CustomerChatRoomResponse getLatestChatRoom() {

        CustomerChatRoom chatRoom =
            chatRoomRepository.getLatestChatRoom(UserProvider.getInstance().getUserId())
                .orElseThrow(() -> new NotFoundException("No exist customer chatting rooms."));

        return CustomerChatRoomResponse.builder()
            .id(chatRoom.getId().getId())
            .title(chatRoom.getTitle())
            .description(chatRoom.getDescription())
            .createdDate(chatRoom.getCreatedDate())
            .lastModifiedDate(chatRoom.getLastModifiedDate())
            .build();
    }

    public Page<CustomerChatRoomResponse> getAll(Pageable pageable) {
        Page<CustomerChatRoom> result = chatRoomRepository.getChatRooms(pageable);

        List<UserResponse> users = UserProvider.getInstance().getUsers();

        return result.map(item -> {

            Long customerId = item.getCustomerId();

            UserResponse customer =
                users.stream()
                    .filter(user -> user.id().equals(customerId))
                    .findAny()
                    .orElse(null);

            return CustomerChatRoomResponse.builder()
                .id(item.getId().getId())
                .title(item.getTitle())
                .customerId(customerId)
                .customer(customer)
                .description(item.getDescription())
                .createdDate(item.getCreatedDate())
                .lastModifiedDate(item.getLastModifiedDate())
                .build();
        });
    }

    public CustomerChatRoomResponse getById(String roomId) {

        CustomerChatRoom room =
            chatRoomRepository.findById(new CustomerChatRoomId(roomId))
                .orElseThrow(() -> new NotFoundException("No exist customer chatting rooms."));

        List<UserResponse> users = UserProvider.getInstance().getUsers();

        UserResponse customer =
            users.stream()
                .filter(user -> user.id().equals(room.getCustomerId())).findAny()
                .orElse(null);

        return CustomerChatRoomResponse.builder()
            .id(room.getId().getId())
            .title(room.getTitle())
            .customerId(room.getCustomerId())
            .customer(customer)
            .description(room.getDescription())
            .createdDate(room.getCreatedDate())
            .lastModifiedDate(room.getLastModifiedDate())
            .build();
    }
}
