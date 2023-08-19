package org.bobpark.eventstoreservice.domain.eventstore.service;

import org.bobpark.eventstoreservice.domain.eventstore.entity.EventId;
import org.bobpark.eventstoreservice.domain.eventstore.model.CompleteEventRequest;
import org.bobpark.eventstoreservice.domain.eventstore.model.CreateEventRequest;
import org.bobpark.eventstoreservice.domain.eventstore.model.EventResponse;

public interface EventService {

    EventResponse createEvent(CreateEventRequest createRequest);

    EventResponse fetch(String eventName);

    EventResponse complete(EventId id, CompleteEventRequest completeRequest);
}
