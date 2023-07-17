package org.bobpark.client.domain.notice.controller.v1;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.client.domain.notice.model.v1.NoticeV1Response;
import org.bobpark.client.domain.notice.service.v1.NoticeCommandV1Service;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "notice")
public class NoticeCommandV1Controller {

    private final NoticeCommandV1Service noticeCommandService;

    @PutMapping(path = "{id}/read")
    public NoticeV1Response readNotice(@PathVariable String id) {
        return noticeCommandService.readNotice(id);
    }

}
