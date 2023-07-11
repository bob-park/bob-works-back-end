package org.bobpark.noticeservice.domain.notice.repository.query;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.bobpark.noticeservice.domain.notice.entity.Notice;
import org.bobpark.noticeservice.domain.notice.model.SearchNoticeRequest;

public interface NoticeQueryRepository {

    Page<Notice> search(SearchNoticeRequest searchRequest, Pageable pageable);

    List<Long> getReadIds(long userId, List<Long> checkIds);

    long countByUnread(long userId);

}
