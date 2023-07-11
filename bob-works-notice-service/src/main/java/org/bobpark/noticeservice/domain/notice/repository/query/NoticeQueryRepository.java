package org.bobpark.noticeservice.domain.notice.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.bobpark.noticeservice.domain.notice.entity.Notice;
import org.bobpark.noticeservice.domain.notice.model.SearchNoticeRequest;

public interface NoticeQueryRepository {

    Page<Notice> search(SearchNoticeRequest searchRequest, Pageable pageable);

    long countByUnread(long userId);

}
