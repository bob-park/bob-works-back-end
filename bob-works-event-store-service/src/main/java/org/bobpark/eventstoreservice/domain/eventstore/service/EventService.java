package org.bobpark.eventstoreservice.domain.eventstore.service;

public interface EventService {

    EventResponse createEvent(CreateEventRequest createRequest);
}
