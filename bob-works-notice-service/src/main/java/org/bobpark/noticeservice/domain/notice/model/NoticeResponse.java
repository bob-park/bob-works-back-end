package org.bobpark.noticeservice.domain.notice.model;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record NoticeResponse(Long id,
                             String title,
                             LocalDateTime createdDate,
                             String createdBy,
                             LocalDateTime lastModifiedDate,
                             String lastModifiedBy,
                             Boolean isRead) {
}
