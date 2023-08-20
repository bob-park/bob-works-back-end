package org.bobpark.maintenanceservice.domain.maintenance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.maintenanceservice.common.utils.user.UserProvider;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoom;
import org.bobpark.maintenanceservice.domain.maintenance.model.CustomerChatRoomResponse;
import org.bobpark.maintenanceservice.domain.maintenance.repository.CustomerChatRoomRepository;

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

}
