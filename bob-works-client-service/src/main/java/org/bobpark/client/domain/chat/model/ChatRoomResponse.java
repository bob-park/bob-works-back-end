package org.bobpark.client.domain.chat.model;

import java.time.LocalDateTime;

public record ChatRoomResponse(Long id,
                               String name,
                               String description,
                               LocalDateTime createdDate,
                               LocalDateTime lastModifiedDate) {

}

