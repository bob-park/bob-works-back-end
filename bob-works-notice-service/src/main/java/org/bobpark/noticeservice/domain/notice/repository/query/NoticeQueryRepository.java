package org.bobpark.noticeservice.domain.notice.repository.query;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.bobpark.noticeservice.domain.notice.entity.Notice;
import org.bobpark.noticeservice.domain.notice.entity.NoticeId;
import org.bobpark.noticeservice.domain.notice.query.v1.SearchNoticeV1Query;

public interface NoticeQueryRepository {

    Page<Notice> search(SearchNoticeV1Query searchQuery, Pageable pageable);

    Optional<Notice> findIncludeReadUser(NoticeId id, long userId);

    List<NoticeId> getUnreadIds(long userId, List<NoticeId> ids);

    long countOfUnread(long userId);

}
