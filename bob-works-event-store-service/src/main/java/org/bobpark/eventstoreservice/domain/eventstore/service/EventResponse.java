package org.bobpark.eventstoreservice.domain.eventstore.service;

import lombok.Builder;

import org.bobpark.eventstoreservice.common.type.EventStatus;

@Builder
public record EventResponse(String id,
                            String eventName,
                            EventStatus status) {
}
