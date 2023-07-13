package org.bobpark.noticeservice.domain.notice.service.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.noticeservice.domain.notice.aggregate.NoticeAggregate;
import org.bobpark.noticeservice.domain.notice.command.v1.CreateNoticeV1Command;
import org.bobpark.noticeservice.domain.notice.model.v1.NoticeV1Response;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class NoticeCommandV1Service {

    private final NoticeAggregate noticeAggregate;

    public NoticeV1Response createNotice(CreateNoticeV1Command createCommand) {
        return noticeAggregate.handleV1CreateNotice(createCommand);
    }

}
