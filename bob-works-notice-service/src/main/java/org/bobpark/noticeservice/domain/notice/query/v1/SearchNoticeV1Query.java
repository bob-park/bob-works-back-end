package org.bobpark.noticeservice.domain.notice.query.v1;

import lombok.Builder;

@Builder
public record SearchNoticeV1Query(Long userId) {
}
