package org.bobpark.client.domain.maintenance.model;

import java.time.LocalDateTime;

import lombok.Builder;

import org.bobpark.client.domain.user.model.UserResponse;

@Builder
public record CustomerChatRoomResponse(String id,
                                       Long customerId,
                                       UserResponse customer,
                                       Long managerId,
                                       String title,
                                       String description,
                                       LocalDateTime createdDate,
                                       String createdBy,
                                       LocalDateTime lastModifiedDate,
                                       String lastModifiedBy) {
}
