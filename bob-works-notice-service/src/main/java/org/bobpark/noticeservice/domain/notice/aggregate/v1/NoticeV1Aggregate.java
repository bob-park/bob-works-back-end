package org.bobpark.noticeservice.domain.notice.aggregate.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import org.bobpark.noticeservice.domain.notice.command.v1.CreateNoticeV1Command;
import org.bobpark.noticeservice.domain.notice.command.v1.ReadNoticeV1Command;
import org.bobpark.noticeservice.domain.notice.entity.NoticeId;
import org.bobpark.noticeservice.domain.notice.event.v1.CreatedNoticeV1Event;
import org.bobpark.noticeservice.domain.notice.event.v1.ReadNoticeV1Event;
import org.bobpark.noticeservice.domain.notice.model.v1.NoticeV1Response;

@Slf4j
@RequiredArgsConstructor
@Component
public class NoticeV1Aggregate {

    private final ApplicationEventPublisher eventPublisher;

    public NoticeV1Response handleCreateNotice(CreateNoticeV1Command createCommand) {

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

    public NoticeV1Response handleReadNotice(ReadNoticeV1Command readCommand) {
        eventPublisher.publishEvent(
            ReadNoticeV1Event.builder()
                .id(readCommand.id())
                .userId(readCommand.userId())
                .build());

        return NoticeV1Response.builder()
            .id(readCommand.id().getId())
            .build();
    }

}
