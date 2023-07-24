package org.bobpark.client.domain.notice.cqrs.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import org.bobpark.client.common.utils.AuthenticationUtils;
import org.bobpark.client.domain.notice.cqrs.event.ReadNoticeEvent;
import org.bobpark.client.domain.notice.fegin.client.NoticeV1Client;

@Slf4j
@RequiredArgsConstructor
@Component
public class NoticeV1EventListener {

    private final NoticeV1Client noticeClient;

    @Async
    @EventListener
    public void handleReadNotice(ReadNoticeEvent readEvent) {



        noticeClient.readNotice(readEvent.authToken(), readEvent.id());

        log.debug("requested read notice. ({})", readEvent);
    }
}
