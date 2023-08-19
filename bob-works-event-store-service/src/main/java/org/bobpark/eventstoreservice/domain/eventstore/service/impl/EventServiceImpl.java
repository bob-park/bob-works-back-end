package org.bobpark.eventstoreservice.domain.eventstore.service.impl;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.eventstoreservice.common.type.EventStatus;
import org.bobpark.eventstoreservice.common.utils.IpAddressUtils;
import org.bobpark.eventstoreservice.domain.eventstore.cqrs.event.CreatedEvent;
import org.bobpark.eventstoreservice.domain.eventstore.entity.Event;
import org.bobpark.eventstoreservice.domain.eventstore.entity.EventId;
import org.bobpark.eventstoreservice.domain.eventstore.model.CreateEventRequest;
import org.bobpark.eventstoreservice.domain.eventstore.model.EventResponse;
import org.bobpark.eventstoreservice.domain.eventstore.repository.EventRepository;
import org.bobpark.eventstoreservice.domain.eventstore.service.EventService;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final ApplicationEventPublisher eventPublisher;

    private final EventRepository eventRepository;

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

    @Transactional
    @Override
    public EventResponse fetch(String eventName) {

        String currentIpAddress = IpAddressUtils.getIpAddress();

        Optional<Event> eventOpt = eventRepository.fetchEvent(eventName, currentIpAddress);

        if (eventOpt.isEmpty()) {
            return null;
        }

        Event event = eventOpt.get();

        event.fetch(eventName, currentIpAddress);

        return EventResponse.builder()
            .id(event.getId().getId())
            .eventName(event.getEventName())
            .eventData(event.getEventData())
            .status(event.getStatus())
            .createdModuleName(event.getCreatedModuleName())
            .createdIpAddress(event.getCreatedIpAddress())
            .executedModuleName(event.getCreatedModuleName())
            .executedIpAddress(event.getExecutedIpAddress())
            .build();
    }

}
