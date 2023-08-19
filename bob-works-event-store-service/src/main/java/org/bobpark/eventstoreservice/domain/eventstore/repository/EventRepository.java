package org.bobpark.eventstoreservice.domain.eventstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.eventstoreservice.domain.eventstore.entity.Event;
import org.bobpark.eventstoreservice.domain.eventstore.entity.EventId;
import org.bobpark.eventstoreservice.domain.eventstore.repository.query.EventQueryRepository;

public interface EventRepository extends JpaRepository<Event, EventId>, EventQueryRepository {
}
