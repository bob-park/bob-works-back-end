package org.bobpark.eventstoreservice.domain.eventstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import org.bobpark.eventstoreservice.common.type.EventStatus;
import org.bobpark.eventstoreservice.common.utils.IpAddressUtils;
import org.bobpark.eventstoreservice.domain.eventstore.cqrs.event.CreatedEvent;
import org.bobpark.eventstoreservice.domain.eventstore.entity.EventId;
import org.bobpark.eventstoreservice.domain.eventstore.service.CreateEventRequest;
import org.bobpark.eventstoreservice.domain.eventstore.service.EventResponse;
import org.bobpark.eventstoreservice.domain.eventstore.service.EventService;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public EventResponse createEvent(CreateEventRequest createRequest) {

        CreatedEvent createdEvent =
            CreatedEvent.builder()
                .id(new EventId())
                .eventName(createRequest.eventName())
                .eventData(createRequest.eventData())
                .createdModuleName(createRequest.createdModuleName())
                .createdIpAddress(IpAddressUtils.getIpAddress())
                .build();

        eventPublisher.publishEvent(createdEvent);

        return EventResponse.builder()
            .id(createdEvent.id().getId())
            .eventName(createdEvent.eventName())
            .status(EventStatus.WAITING)
            .build();
    }

}
