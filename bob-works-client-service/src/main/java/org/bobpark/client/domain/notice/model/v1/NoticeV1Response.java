package org.bobpark.client.domain.notice.model.v1;

import java.time.LocalDateTime;

public record NoticeV1Response(String id,
                               String title,
                               String description,
                               LocalDateTime createdDate,
                               String createdBy,
                               LocalDateTime lastModifiedDate,
                               String lastModifiedBy,
                               Boolean isRead) {
}

