package org.bobpark.noticeservice.domain.notice.controller.v1;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.noticeservice.domain.notice.model.v1.CreateNoticeV1Request;
import org.bobpark.noticeservice.domain.notice.model.v1.NoticeV1Response;
import org.bobpark.noticeservice.domain.notice.service.v1.NoticeCommandV1Service;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/notice")
public class NoticeCommandV1Controller {

    private final NoticeCommandV1Service noticeCommandService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public NoticeV1Response createNotice(@RequestBody CreateNoticeV1Request createRequest) {
        return noticeCommandService.createNotice(createRequest);
    }
}
