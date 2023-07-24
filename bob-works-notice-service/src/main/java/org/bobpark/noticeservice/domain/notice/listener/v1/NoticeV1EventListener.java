package org.bobpark.noticeservice.domain.notice.listener.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.noticeservice.domain.notice.entity.Notice;
import org.bobpark.noticeservice.domain.notice.event.v1.CreatedNoticeV1Event;
import org.bobpark.noticeservice.domain.notice.event.v1.ReadNoticeV1Event;
import org.bobpark.noticeservice.domain.notice.repository.NoticeRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class NoticeV1EventListener {

    private final NoticeRepository noticeRepository;

    @EventListener
    public void createdNotice(CreatedNoticeV1Event createdEvent) {
        Notice createdNotice =
            Notice.builder()
                .id(createdEvent.id())
                .title(createdEvent.title())
                .description(createdEvent.description())
                .build();

        noticeRepository.save(createdNotice);

        log.debug("created notice. ({})", createdEvent);
    }

    @EventListener
    public void readNotice(ReadNoticeV1Event readEvent) {

        Notice notice =
            noticeRepository.findIncludeReadUser(readEvent.id(), readEvent.userId())
                .orElseThrow(() -> new NotFoundException(Notice.class, readEvent.id()));

        notice.addReadUser(readEvent.userId());

        log.debug("read notice. (noticeId={}, userId={})", notice.getId(), readEvent.userId());
    }

}
