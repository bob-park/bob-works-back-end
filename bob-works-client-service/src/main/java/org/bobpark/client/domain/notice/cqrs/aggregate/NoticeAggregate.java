package org.bobpark.client.domain.notice.cqrs.aggregate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import org.bobpark.client.common.utils.AuthenticationUtils;
import org.bobpark.client.domain.notice.cqrs.command.ReadNoticeCommand;
import org.bobpark.client.domain.notice.cqrs.event.ReadNoticeEvent;

@Slf4j
@RequiredArgsConstructor
@Component
public class NoticeAggregate {

    private final ApplicationEventPublisher publisher;

    public String handleReadNoticeCommand(ReadNoticeCommand readCommand) {
        String token = AuthenticationUtils.getInstance().getBearerToken();

        ReadNoticeEvent readEvent = new ReadNoticeEvent(token, readCommand.id());

        publisher.publishEvent(readEvent);

        log.debug("publish read notice event. ({})", readEvent);

        return readEvent.id();
    }
}
