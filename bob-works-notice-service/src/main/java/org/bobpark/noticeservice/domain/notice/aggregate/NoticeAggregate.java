package org.bobpark.noticeservice.domain.notice.aggregate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import org.bobpark.noticeservice.domain.notice.command.v1.CreateNoticeV1Command;
import org.bobpark.noticeservice.domain.notice.entity.NoticeId;
import org.bobpark.noticeservice.domain.notice.event.v1.CreatedNoticeV1Event;
import org.bobpark.noticeservice.domain.notice.model.v1.NoticeV1Response;

@Slf4j
@RequiredArgsConstructor
@Component
public class NoticeAggregate {

    private final ApplicationEventPublisher eventPublisher;

    public NoticeV1Response handleV1CreateNotice(CreateNoticeV1Command createCommand) {

        NoticeId noticeId = new NoticeId();

        eventPublisher.publishEvent(
            CreatedNoticeV1Event.builder()
                .id(noticeId)
                .title(createCommand.title())
                .description(createCommand.description())
                .build());

        return NoticeV1Response.builder()
            .id(noticeId.getId())
            .title(createCommand.title())
            .description(createCommand.description())
            .build();
    }

}
