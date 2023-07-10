package org.bobpark.noticeservice.domain.notice.model;

import java.time.LocalDateTime;

public record NoticeResponse(Long id,
                             String title,
                             LocalDateTime createdDate,
                             String createdBy,
                             LocalDateTime lastModifiedDate,
                             String lastModifiedBy) {
}
