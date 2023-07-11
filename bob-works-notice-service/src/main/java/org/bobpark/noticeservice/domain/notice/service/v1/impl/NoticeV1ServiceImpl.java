package org.bobpark.noticeservice.domain.notice.service.v1.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.noticeservice.domain.notice.entity.Notice;
import org.bobpark.noticeservice.domain.notice.model.SearchNoticeRequest;
import org.bobpark.noticeservice.domain.notice.model.v1.NoticeV1Response;
import org.bobpark.noticeservice.domain.notice.model.v1.UnReadNoticeCountV1Response;
import org.bobpark.noticeservice.domain.notice.repository.NoticeRepository;
import org.bobpark.noticeservice.domain.notice.service.v1.NoticeV1Service;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class NoticeV1ServiceImpl implements NoticeV1Service {

    private final NoticeRepository noticeRepository;

    @Override
    public UnReadNoticeCountV1Response countOfUnread(long userId) {

        long count = noticeRepository.countByUnread(userId);

        return new UnReadNoticeCountV1Response(count);
    }

    @Override
    public Page<NoticeV1Response> search(long userId, SearchNoticeRequest searchRequest,
        Pageable pageable) {

        Page<Notice> result = noticeRepository.search(searchRequest, pageable);
        List<Long> ids = result.getContent().stream().map(Notice::getId).toList();
        List<Long> readIds = noticeRepository.getReadIds(userId, ids);

        return result.map(item ->
            NoticeV1Response.builder()
                .id(item.getId())
                .title(item.getTitle())
                .createdDate(item.getCreatedDate())
                .createdBy(item.getCreatedBy())
                .isRead(readIds.contains(item.getId()))
                .build());
    }

    @Transactional
    @Override
    public NoticeV1Response read(Id<Notice, Long> noticeId, long userId) {

        Notice notice =
            noticeRepository.findById(noticeId.getValue())
                .orElseThrow(() -> new NotFoundException(noticeId));

        notice.addReadUser(userId);

        log.debug("read notice. (noticeId={}, userId={})", notice.getId(), userId);

        return NoticeV1Response.builder()
            .id(notice.getId())
            .title(notice.getTitle())
            .createdDate(notice.getCreatedDate())
            .createdBy(notice.getCreatedBy())
            .build();
    }

    @Override
    public NoticeV1Response getNotice(Id<Notice, Long> noticeId) {

        Notice notice =
            noticeRepository.findById(noticeId.getValue())
                .orElseThrow(() -> new NotFoundException(noticeId));

        return NoticeV1Response.builder()
            .id(notice.getId())
            .title(notice.getTitle())
            .description(notice.getDescription())
            .createdDate(notice.getCreatedDate())
            .createdBy(notice.getCreatedBy())
            .build();
    }

}
