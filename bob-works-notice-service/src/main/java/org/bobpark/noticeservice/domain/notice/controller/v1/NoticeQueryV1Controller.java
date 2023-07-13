package org.bobpark.noticeservice.domain.notice.controller.v1;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.noticeservice.domain.notice.entity.NoticeId;
import org.bobpark.noticeservice.domain.notice.model.v1.NoticeV1Response;
import org.bobpark.noticeservice.domain.notice.model.v1.SearchNoticeV1Request;
import org.bobpark.noticeservice.domain.notice.service.v1.NoticeQueryV1Service;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/notice")
public class NoticeQueryV1Controller {

    private final NoticeQueryV1Service noticeQueryService;

    @GetMapping(path = "search")
    public Page<NoticeV1Response> search(SearchNoticeV1Request searchRequest, @PageableDefault Pageable pageable) {
        return noticeQueryService.search(searchRequest, pageable);
    }

    @GetMapping(path = "{noticeId}")
    public NoticeV1Response getNotice(@PathVariable String noticeId) {
        return noticeQueryService.getNotice(new NoticeId(noticeId));
    }
}
