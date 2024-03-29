package org.bobpark.noticeservice.domain.notice.service.v1;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.noticeservice.common.utils.UserProvider;
import org.bobpark.noticeservice.domain.notice.entity.Notice;
import org.bobpark.noticeservice.domain.notice.entity.NoticeId;
import org.bobpark.noticeservice.domain.notice.model.v1.NoticeV1Response;
import org.bobpark.noticeservice.domain.notice.model.v1.SearchNoticeV1Request;
import org.bobpark.noticeservice.domain.notice.model.v1.UnReadNoticeCountV1Response;
import org.bobpark.noticeservice.domain.notice.query.v1.SearchNoticeV1Query;
import org.bobpark.noticeservice.domain.notice.repository.NoticeRepository;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class NoticeQueryV1Service {

    private final NoticeRepository noticeRepository;

    public Page<NoticeV1Response> search(SearchNoticeV1Request searchRequest, Pageable pageable) {

        long userId = UserProvider.getInstance().getUserId();

        SearchNoticeV1Query searchQuery =
            SearchNoticeV1Query.builder()
                .build();

        Page<Notice> result = noticeRepository.search(searchQuery, pageable);

        List<NoticeId> ids = result.getContent().stream().map(Notice::getId).toList();

        List<NoticeId> unreadIds = noticeRepository.getUnreadIds(userId, ids);

        return result.map(item ->
            NoticeV1Response.builder()
                .id(item.getId().getId())
                .title(item.getTitle())
                .createdDate(item.getCreatedDate())
                .createdBy(item.getCreatedBy())
                .lastModifiedDate(item.getCreatedDate())
                .lastModifiedBy(item.getLastModifiedBy())
                .isRead(!unreadIds.contains(item.getId()))
                .build());
    }

    public NoticeV1Response getNotice(NoticeId id) {
        Notice notice =
            noticeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Notice.class, id.getId()));

        return NoticeV1Response.builder()
            .id(notice.getId().getId())
            .title(notice.getTitle())
            .description(notice.getDescription())
            .createdDate(notice.getCreatedDate())
            .createdBy(notice.getCreatedBy())
            .lastModifiedDate(notice.getLastModifiedDate())
            .lastModifiedBy(notice.getLastModifiedBy())
            .build();
    }

    public UnReadNoticeCountV1Response countUnread() {
        long userId = UserProvider.getInstance().getUserId();
        long count = noticeRepository.countOfUnread(userId);

        return new UnReadNoticeCountV1Response(count);
    }

}
