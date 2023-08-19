package org.bobpark.eventstoreservice.domain.eventstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.eventstoreservice.domain.eventstore.entity.Event;
import org.bobpark.eventstoreservice.domain.eventstore.entity.EventId;

public interface EventRepository extends JpaRepository<Event, EventId> {
}
