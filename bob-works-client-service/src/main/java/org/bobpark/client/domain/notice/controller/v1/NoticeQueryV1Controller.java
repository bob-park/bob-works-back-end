package org.bobpark.client.domain.notice.controller.v1;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.client.common.page.Page;
import org.bobpark.client.domain.notice.model.v1.NoticeV1Response;
import org.bobpark.client.domain.notice.service.v1.NoticeQueryV1Service;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "notice")
public class NoticeQueryV1Controller {

    private final NoticeQueryV1Service noticeQueryService;

    @GetMapping(path = "search")
    public Page<NoticeV1Response> search(Pageable pageable) {
        return noticeQueryService.search(pageable);
    }
}
