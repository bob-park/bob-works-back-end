package org.bobpark.noticeservice.domain.notice.service.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.bobpark.core.model.common.Id;
import org.bobpark.noticeservice.domain.notice.entity.Notice;
import org.bobpark.noticeservice.domain.notice.model.SearchNoticeRequest;
import org.bobpark.noticeservice.domain.notice.model.v1.NoticeV1Response;
import org.bobpark.noticeservice.domain.notice.model.v1.UnReadNoticeCountV1Response;

public interface NoticeV1Service {

    UnReadNoticeCountV1Response countOfUnread(long userId);

    Page<NoticeV1Response> search(long userId, SearchNoticeRequest searchRequest, Pageable pageable);

    NoticeV1Response read(Id<Notice, Long> noticeId, long userId);

    NoticeV1Response getNotice(Id<Notice, Long> noticeId);
}
