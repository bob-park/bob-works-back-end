package org.bobpark.client.domain.notice.service.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import org.bobpark.client.domain.notice.cqrs.aggregate.NoticeAggregate;
import org.bobpark.client.domain.notice.cqrs.command.ReadNoticeCommand;
import org.bobpark.client.domain.notice.model.v1.NoticeV1Response;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeCommandV1Service {

    private final NoticeAggregate noticeAggregate;

    public NoticeV1Response readNotice(String id) {
        String readId = noticeAggregate.handleReadNoticeCommand(new ReadNoticeCommand(id));

        return NoticeV1Response.builder()
            .id(readId)
            .build();
    }

}
