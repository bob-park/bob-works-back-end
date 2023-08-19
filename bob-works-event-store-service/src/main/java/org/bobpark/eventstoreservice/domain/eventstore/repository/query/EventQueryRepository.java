package org.bobpark.eventstoreservice.domain.eventstore.repository.query;

import java.util.Optional;

import org.bobpark.eventstoreservice.domain.eventstore.entity.Event;

public interface EventQueryRepository {

    Optional<Event> fetchEvent(String eventName, String ipAddress);
}
