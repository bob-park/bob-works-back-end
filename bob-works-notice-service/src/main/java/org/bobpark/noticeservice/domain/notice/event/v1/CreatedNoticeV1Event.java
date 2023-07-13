package org.bobpark.noticeservice.domain.notice.event.v1;

import lombok.Builder;

import org.bobpark.noticeservice.domain.notice.entity.NoticeId;

@Builder
public record CreatedNoticeV1Event(NoticeId id,
                                   String title,
                                   String description) {
}
