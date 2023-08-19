package org.bobpark.eventstoreservice.domain.eventstore.cqrs.event;

import java.util.Map;

import lombok.Builder;

import org.bobpark.eventstoreservice.domain.eventstore.entity.EventId;

@Builder
public record CreatedEvent(EventId id,
                           String eventName,
                           Map<String, Object> eventData,
                           String createdModuleName,
                           String createdIpAddress) {
}
