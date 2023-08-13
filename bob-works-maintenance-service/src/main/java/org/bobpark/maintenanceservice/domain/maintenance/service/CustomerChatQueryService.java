package org.bobpark.maintenanceservice.domain.maintenance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChat;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoomId;
import org.bobpark.maintenanceservice.domain.maintenance.model.CustomerChatResponse;
import org.bobpark.maintenanceservice.domain.maintenance.model.CustomerChatRoomResponse;
import org.bobpark.maintenanceservice.domain.maintenance.model.SearchCustomerChatCondition;
import org.bobpark.maintenanceservice.domain.maintenance.repository.CustomerChatRepository;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CustomerChatQueryService {

    private final CustomerChatRepository chatRepository;

    public Page<CustomerChatResponse> searchChatByRoom(CustomerChatRoomId roomId, Pageable pageable) {

        SearchCustomerChatCondition searchCondition =
            SearchCustomerChatCondition.builder()
                .roomId(roomId.getId())
                .build();

        Page<CustomerChat> result = chatRepository.search(searchCondition, pageable);

        return result.map(item ->
            CustomerChatResponse.builder()
                .id(item.getId().getId())
                .room(
                    CustomerChatRoomResponse.builder()
                        .id(item.getRoom().getId().getId())
                        .customerId(item.getRoom().getCustomerId())
                        .title(item.getRoom().getTitle())
                        .description(item.getRoom().getDescription())
                        .build())
                .writerId(item.getWriterId())
                .contents(item.getContents())
                .isRead(item.getIsRead())
                .createdDate(item.getCreatedDate())
                .createdBy(item.getCreatedBy())
                .lastModifiedDate(item.getLastModifiedDate())
                .lastModifiedBy(item.getLastModifiedBy())
                .build());
    }
}
