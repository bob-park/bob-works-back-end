package org.bobpark.noticeservice.domain.notice.repository.query;

import org.bobpark.noticeservice.domain.notice.entity.NoticeId;

public interface NoticeReadUserQueryRepository {

    boolean isRead(NoticeId noticeId, long userId);

}
